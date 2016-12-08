/*
 * Copyright 2015, Google Inc. All rights reserved.
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

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import io.grpc.InternalKnownTransport;
import io.grpc.InternalMethodDescriptor;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;
import io.grpc.internal.AbstractClientStream2;
import io.grpc.internal.Http2ClientStreamTransportState;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.WritableBuffer;

/**
 * Client stream for a Netty transport. Must only be called from the sending application
 * thread.
 */
class JettyClientStream extends AbstractClientStream2 {
  private static final InternalMethodDescriptor methodDescriptorAccessor =
      new InternalMethodDescriptor(InternalKnownTransport.NETTY);

  private final Sink sink = new Sink();
  private final TransportState state;
  private final MethodDescriptor<?, ?> method;
  /** {@code null} after start. */
  private Metadata headers;
  private String authority;
  private final String scheme;
  private final String userAgent;

  JettyClientStream(TransportState state, MethodDescriptor<?, ?> method, Metadata headers,
      String authority, String scheme, String userAgent,
      StatsTraceContext statsTraceCtx) {
    super(new JettyWritableBufferAllocator(), statsTraceCtx);
    this.state = checkNotNull(state, "transportState");
    this.method = checkNotNull(method, "method");
    this.headers = checkNotNull(headers, "headers");
    this.authority = checkNotNull(authority, "authority");
    this.scheme = checkNotNull(scheme, "scheme");
    this.userAgent = userAgent;
  }

  @Override
  protected TransportState transportState() {
    return state;
  }

  @Override
  protected Sink abstractClientStreamSink() {
    return sink;
  }

  @Override
  public void setAuthority(String authority) {
    checkState(headers != null, "must be call before start");
    this.authority = checkNotNull(authority, "authority");
  }

  private class Sink implements AbstractClientStream2.Sink {

    @Override
    public void writeFrame(WritableBuffer frame, boolean endOfStream, boolean flush) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void request(int numMessages) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void cancel(Status status) {
      // TODO Auto-generated method stub
      
    }
    
  }

  /** This should only called from the transport thread. */
  public abstract static class TransportState extends Http2ClientStreamTransportState {

    protected TransportState(int maxMessageSize, StatsTraceContext statsTraceCtx) {
      super(maxMessageSize, statsTraceCtx);
      // TODO Auto-generated constructor stub
    }

    @Override
    public void bytesRead(int numBytes) {
      // TODO Auto-generated method stub

    }

    @Override
    protected void http2ProcessingFailed(Status status, Metadata trailers) {
      // TODO Auto-generated method stub

    }

    @Override
    protected void deframeFailed(Throwable cause) {
      // TODO Auto-generated method stub

    }

  }

}
