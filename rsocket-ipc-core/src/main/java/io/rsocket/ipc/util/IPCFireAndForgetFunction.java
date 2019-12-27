/*
 * Copyright 2019 the original author or authors.
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
package io.rsocket.ipc.util;

import io.rsocket.Payload;
import io.rsocket.ipc.Functions;
import io.rsocket.ipc.Marshaller;
import io.rsocket.ipc.MetadataDecoder;
import io.rsocket.ipc.Unmarshaller;
import reactor.core.publisher.Mono;

@SuppressWarnings("rawtypes")
public class IPCFireAndForgetFunction implements IPCFunction<Mono<Void>> {

  final String route;
  final Unmarshaller unmarshaller;
  final Marshaller marshaller;
  final Functions.FireAndForget fnf;

  public IPCFireAndForgetFunction(
      String route, Unmarshaller unmarshaller, Marshaller marshaller, Functions.FireAndForget fnf) {
    this.route = route;
    this.unmarshaller = unmarshaller;
    this.marshaller = marshaller;
    this.fnf = fnf;
  }

  @Override
  @SuppressWarnings("unchecked")
  public Mono<Void> apply(Payload payload, MetadataDecoder.Metadata metadata) {
    Object input = unmarshaller.apply(payload.sliceData());
    return fnf.apply(input, metadata.metadata());
  }
}
