// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

package org.apache.doris.nereids.trees.plans.logical;

import org.apache.doris.catalog.Table;
import org.apache.doris.nereids.trees.NodeType;
import org.apache.doris.nereids.trees.expressions.Slot;
import org.apache.doris.nereids.trees.expressions.SlotReference;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Logical relation plan node.
 */
public class LogicalRelation extends LogicalLeaf {
    private final Table table;
    private final List<String> qualifier;

    /**
     * Constructor for LogicalRelationPlan.
     *
     * @param table Doris table
     * @param qualifier qualified relation name
     */
    public LogicalRelation(Table table, List<String> qualifier) {
        super(NodeType.LOGICAL_BOUND_RELATION);
        this.table = table;
        this.qualifier = qualifier;
        this.output = table.getBaseSchema()
                .stream()
                .map(col -> SlotReference.fromColumn(col, qualifier))
                .collect(Collectors.toList());
    }

    public Table getTable() {
        return table;
    }

    public List<String> getQualifier() {
        return qualifier;
    }

    @Override
    public List<Slot> getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "Relation(" + StringUtils.join(qualifier, ".") + "." + table.getName()
            + ", output: " + StringUtils.join(output, ", ")
            + ")";
    }
}
