/*
 * Copyright 2014, Google Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *    * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *
 *    * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.grpc.jetty;



import java.net.SocketAddress;
import java.util.concurrent.Executor;

import javax.annotation.Nullable;

import com.google.common.base.Preconditions;

import io.grpc.Attributes;
import io.grpc.CallOptions;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.ClientStream;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.GrpcUtil;
import io.grpc.internal.LogId;
import io.grpc.internal.StatsTraceContext;

/**
 * A Netty-based {@link ConnectionClientTransport} implementation.
 */
class JettyClientTransport implements ConnectionClientTransport {
  private final LogId logId = LogId.allocate(getClass().getName());
  private final SocketAddress address;
  private final String authority;
  private final String userAgent;
  private final int flowControlWindow;
  private final int maxMessageSize;
  private final int maxHeaderListSize;

  JettyClientTransport(
      SocketAddress address, int flowControlWindow,
      int maxMessageSize, int maxHeaderListSize,
      String authority, @Nullable String userAgent) {
    this.address = Preconditions.checkNotNull(address, "address");
    this.flowControlWindow = flowControlWindow;
    this.maxMessageSize = maxMessageSize;
    this.maxHeaderListSize = maxHeaderListSize;
    this.authority = authority;
    this.userAgent = GrpcUtil.getGrpcUserAgent("jetty", userAgent);
  }

  @Override
  public String toString() {
    return getLogId() + "(" + address + ")";
  }

  @Override
  public LogId getLogId() {
    return logId;
  }

  @Override
  public Attributes getAttrs() {
    // TODO(zhangkun83): fill channel security attributes
    return Attributes.EMPTY;
  }

  @Override
  public Runnable start(Listener listener) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void shutdown() {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void shutdownNow(Status reason) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public ClientStream newStream(MethodDescriptor<?, ?> method, Metadata headers,
      CallOptions callOptions, StatsTraceContext statsTraceCtx) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ClientStream newStream(MethodDescriptor<?, ?> method, Metadata headers) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void ping(PingCallback callback, Executor executor) {
    // TODO Auto-generated method stub
    
  }

}
