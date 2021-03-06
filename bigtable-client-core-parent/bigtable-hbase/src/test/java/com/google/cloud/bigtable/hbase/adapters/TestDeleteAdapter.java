/*
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.cloud.bigtable.hbase.adapters;

import java.util.concurrent.TimeUnit;

import com.google.cloud.bigtable.data.v2.internal.RequestContext;
import com.google.cloud.bigtable.data.v2.models.InstanceName;
import com.google.cloud.bigtable.data.v2.models.RowMutation;
import com.google.protobuf.ByteString;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.google.bigtable.v2.MutateRowRequest;
import com.google.bigtable.v2.Mutation;
import com.google.bigtable.v2.Mutation.MutationCase;
import com.google.bigtable.v2.TimestampRange;
import com.google.cloud.bigtable.hbase.DataGenerationHelper;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class TestDeleteAdapter {

  private static final String PROJECT_ID = "test-project-id";
  private static final String INSTANCE_ID = "test-instance-id";
  private static final String TABLE_ID = "test-table-id";
  private static final String APP_PROFILE_ID = "test-app-profile-id";
  private static final RequestContext REQUEST_CONTEXT = RequestContext.create(
      InstanceName.of(PROJECT_ID, INSTANCE_ID),
      APP_PROFILE_ID
  );

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  protected DeleteAdapter deleteAdapter = new DeleteAdapter();
  protected QualifierTestHelper qualifierTestHelper = new QualifierTestHelper();
  protected DataGenerationHelper randomHelper = new DataGenerationHelper();

  @Test
  public void testFullRowDelete() {
    byte[] rowKey = randomHelper.randomData("rk1-");
    Delete delete = new Delete(rowKey);
    com.google.cloud.bigtable.data.v2.models.Mutation mutation =
        com.google.cloud.bigtable.data.v2.models.Mutation.create();
    deleteAdapter.adapt(delete, mutation);
    MutateRowRequest rowMutation = toMutateRowRequest(rowKey, mutation);

    Assert.assertArrayEquals(rowKey, rowMutation.getRowKey().toByteArray());
    Assert.assertEquals(1, rowMutation.getMutationsCount());

    Mutation.MutationCase mutationCase = rowMutation.getMutations(0).getMutationCase();

    Assert.assertEquals(MutationCase.DELETE_FROM_ROW, mutationCase);

    testTwoWayAdapt(delete, deleteAdapter);
  }

  @Test
  public void testDeleteRowAtTimestampIsUnsupported() {
    byte[] rowKey = randomHelper.randomData("rk1-");
    Delete delete = new Delete(rowKey, 1000L);

    expectedException.expect(UnsupportedOperationException.class);
    expectedException.expectMessage("Cannot perform row deletion at timestamp");

    deleteAdapter.adapt(delete, com.google.cloud.bigtable.data.v2.models.Mutation.create());
  }

  @Test
  public void testColumnFamilyDelete() {
    byte[] rowKey = randomHelper.randomData("rk1-");
    byte[] family = randomHelper.randomData("family1-");
    Delete delete = new Delete(rowKey);
    delete.addFamily(family);
    com.google.cloud.bigtable.data.v2.models.Mutation mutation =
        com.google.cloud.bigtable.data.v2.models.Mutation.create();
    deleteAdapter.adapt(delete, mutation);
    MutateRowRequest rowMutation = toMutateRowRequest(rowKey, mutation);

    Assert.assertArrayEquals(rowKey, rowMutation.getRowKey().toByteArray());
    Assert.assertEquals(1, rowMutation.getMutationsCount());

    MutationCase mutationCase = rowMutation.getMutations(0).getMutationCase();

    Assert.assertEquals(MutationCase.DELETE_FROM_FAMILY, mutationCase);

    Mutation.DeleteFromFamily deleteFromFamily =
        rowMutation.getMutations(0).getDeleteFromFamily();
    Assert.assertArrayEquals(family, deleteFromFamily.getFamilyNameBytes().toByteArray());

    testTwoWayAdapt(delete, deleteAdapter);
  }

  @Test
  public void testColumnFamilyDeleteAtTimestampFails() {
    byte[] rowKey = randomHelper.randomData("rk1-");
    Delete delete = new Delete(rowKey);
    delete.addFamily(Bytes.toBytes("family1"), 10000L);

    expectedException.expect(UnsupportedOperationException.class);
    expectedException.expectMessage("Cannot perform column family deletion before timestamp");

    deleteAdapter.adapt(delete, com.google.cloud.bigtable.data.v2.models.Mutation.create());
  }

  @Test
  public void testDeleteColumnAtTimestamp() {
    byte[] rowKey = randomHelper.randomData("rk1-");
    byte[] family = randomHelper.randomData("family1-");
    byte[] qualifier = randomHelper.randomData("qualifier");
    long hbaseTimestamp = 1000L;
    long bigtableStartTimestamp = TimeUnit.MILLISECONDS.toMicros(hbaseTimestamp);
    long bigtableEndTimestamp = TimeUnit.MILLISECONDS.toMicros(hbaseTimestamp + 1);

    Delete delete = new Delete(rowKey);
    delete.addColumn(family, qualifier, hbaseTimestamp);
    com.google.cloud.bigtable.data.v2.models.Mutation mutation =
        com.google.cloud.bigtable.data.v2.models.Mutation.create();
    deleteAdapter.adapt(delete, mutation);
    MutateRowRequest rowMutation = toMutateRowRequest(rowKey, mutation);

    Assert.assertArrayEquals(rowKey, rowMutation.getRowKey().toByteArray());
    Assert.assertEquals(1, rowMutation.getMutationsCount());

    MutationCase mutationCase = rowMutation.getMutations(0).getMutationCase();

    Assert.assertEquals(MutationCase.DELETE_FROM_COLUMN, mutationCase);

    Mutation.DeleteFromColumn deleteFromColumn =
        rowMutation.getMutations(0).getDeleteFromColumn();
    Assert.assertArrayEquals(family, deleteFromColumn.getFamilyNameBytes().toByteArray());
    Assert.assertArrayEquals(qualifier, deleteFromColumn.getColumnQualifier().toByteArray());
    Assert.assertTrue(rowMutation.getMutations(0).getDeleteFromColumn().hasTimeRange());

    TimestampRange timeStampRange = deleteFromColumn.getTimeRange();
    Assert.assertEquals(bigtableStartTimestamp, timeStampRange.getStartTimestampMicros());
    Assert.assertEquals(bigtableEndTimestamp, timeStampRange.getEndTimestampMicros());

    testTwoWayAdapt(delete, deleteAdapter);
  }

  @Test
  public void testDeleteLatestColumnThrows() {
    byte[] rowKey = randomHelper.randomData("rk1-");
    byte[] family = randomHelper.randomData("family1-");
    byte[] qualifier = randomHelper.randomData("qualifier");

    Delete delete = new Delete(rowKey);
    delete.addColumn(family, qualifier);

    expectedException.expect(UnsupportedOperationException.class);
    expectedException.expectMessage("Cannot delete single latest cell");

    deleteAdapter.adapt(delete, com.google.cloud.bigtable.data.v2.models.Mutation.create());
  }

  @Test
  public void testDeleteColumnBeforeTimestamp() {
    byte[] rowKey = randomHelper.randomData("rk1-");
    byte[] family = randomHelper.randomData("family1-");
    byte[] qualifier = randomHelper.randomData("qualifier");
    long hbaseTimestamp = 1000L;
    long bigtableTimestamp = TimeUnit.MILLISECONDS.toMicros(hbaseTimestamp + 1);

    Delete delete = new Delete(rowKey);
    delete.addColumns(family, qualifier, hbaseTimestamp);
    com.google.cloud.bigtable.data.v2.models.Mutation mutation =
        com.google.cloud.bigtable.data.v2.models.Mutation.create();
    deleteAdapter.adapt(delete, mutation);
    MutateRowRequest rowMutation = toMutateRowRequest(rowKey, mutation);

    Assert.assertArrayEquals(rowKey, rowMutation.getRowKey().toByteArray());
    Assert.assertEquals(1, rowMutation.getMutationsCount());
    Assert.assertEquals(
        MutationCase.DELETE_FROM_COLUMN, rowMutation.getMutations(0).getMutationCase());

    Mutation.DeleteFromColumn deleteFromColumn =
        rowMutation.getMutations(0).getDeleteFromColumn();
    Assert.assertArrayEquals(qualifier, deleteFromColumn.getColumnQualifier().toByteArray());
    Assert.assertTrue(rowMutation.getMutations(0).getDeleteFromColumn().hasTimeRange());

    TimestampRange timeRange = deleteFromColumn.getTimeRange();
    Assert.assertEquals(0L, timeRange.getStartTimestampMicros());
    Assert.assertEquals(bigtableTimestamp, timeRange.getEndTimestampMicros());

    testTwoWayAdapt(delete, deleteAdapter);
  }

  @Test
  public void testDeleteFamilyVersionIsUnsupported() {
    // Unexpected to see this:
    byte[] rowKey = randomHelper.randomData("rk1-");
    byte[] family = randomHelper.randomData("family1-");
    long hbaseTimestamp = 1000L;

    Delete delete = new Delete(rowKey);
    delete.addFamilyVersion(family, hbaseTimestamp);

    expectedException.expect(UnsupportedOperationException.class);
    expectedException.expectMessage("Cannot perform column family deletion at timestamp");

    deleteAdapter.adapt(delete, com.google.cloud.bigtable.data.v2.models.Mutation.create());
  }

  /**
   * Convert the {@link Delete} to a {@link Mutation}, back to a {@link Delete}, back to
   * {@link Mutation}. Compare the two mutations for equality. This ensures that the adapt
   * process is idempotent.
   */
  private void testTwoWayAdapt(Delete delete, DeleteAdapter adapter) {
    // delete -> mutation
    com.google.cloud.bigtable.data.v2.models.Mutation firstMutation =
        com.google.cloud.bigtable.data.v2.models.Mutation.create();
    adapter.adapt(delete, firstMutation);
    MutateRowRequest firstAdapt = toMutateRowRequest(delete.getRow(), firstMutation);
    // mutation -> delete -> mutation;
    com.google.cloud.bigtable.data.v2.models.Mutation secondMutation =
        com.google.cloud.bigtable.data.v2.models.Mutation.create();
    adapter.adapt(adapter.adapt(firstAdapt), secondMutation);
    MutateRowRequest secondAdapt = toMutateRowRequest(delete.getRow(), secondMutation);
    // The round trips
    Assert.assertEquals(firstAdapt, secondAdapt);
  }

  private MutateRowRequest toMutateRowRequest(byte[] rowKey, com.google.cloud.bigtable.data.v2.models.Mutation mutation) {
    RowMutation rowMutation = toRowMutationModel(rowKey, mutation);
    MutateRowRequest.Builder builder = rowMutation.toProto(REQUEST_CONTEXT).toBuilder();
    return builder.build();
  }

  private RowMutation toRowMutationModel(byte [] rowKey, com.google.cloud.bigtable.data.v2.models.Mutation mutation) {
    return RowMutation.create(TABLE_ID, ByteString.copyFrom(rowKey), mutation);
  }
}