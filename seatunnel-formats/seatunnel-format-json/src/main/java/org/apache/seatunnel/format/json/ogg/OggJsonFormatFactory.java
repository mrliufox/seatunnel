/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.seatunnel.format.json.ogg;

import org.apache.seatunnel.api.configuration.util.OptionRule;
import org.apache.seatunnel.api.serialization.DeserializationSchema;
import org.apache.seatunnel.api.serialization.SerializationSchema;
import org.apache.seatunnel.api.table.connector.DeserializationFormat;
import org.apache.seatunnel.api.table.connector.SerializationFormat;
import org.apache.seatunnel.api.table.factory.DeserializationFormatFactory;
import org.apache.seatunnel.api.table.factory.SerializationFormatFactory;
import org.apache.seatunnel.api.table.factory.TableFactoryContext;

import java.util.Map;

/**
 * Format factory for providing configured instances of Ogg JSON to RowData {@link
 * DeserializationSchema}.
 */
public class OggJsonFormatFactory
        implements DeserializationFormatFactory, SerializationFormatFactory {

    public static final String IDENTIFIER = "ogg_json";

    @Override
    public String factoryIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public OptionRule optionRule() {
        // TODO config option rules
        return OptionRule.builder().build();
    }

    @Override
    public SerializationFormat createSerializationFormat(TableFactoryContext context) {
        return new SerializationFormat() {
            @Override
            public SerializationSchema createSerializationSchema() {
                return new OggJsonSerializationSchema(null);
            }
        };
    }

    @Override
    public DeserializationFormat createDeserializationFormat(TableFactoryContext context) {
        Map<String, String> options = context.getOptions().toMap();
        boolean ignoreParseErrors = OggJsonFormatOptions.getIgnoreParseErrors(options);
        String databaseInclude = OggJsonFormatOptions.getDatabaseInclude(options);
        String tableInclude = OggJsonFormatOptions.getTableInclude(options);

        // TODO config SeaTunnelRowType
        return new DeserializationFormat() {
            @Override
            public DeserializationSchema createDeserializationSchema() {
                return new OggJsonDeserializationSchema(
                        null, databaseInclude, tableInclude, ignoreParseErrors);
            }
        };
    }
}
