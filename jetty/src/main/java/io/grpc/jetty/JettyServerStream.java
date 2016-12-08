/*
 * Copyright 2014, Google Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer. * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the documentation and/or other
 * materials provided with the distribution.
 *
 * * Neither the name of Google Inc. nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package io.grpc.jetty;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.logging.Logger;

import io.grpc.Attributes;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.internal.AbstractServerStream;
import io.grpc.internal.ServerStreamListener;
import io.grpc.internal.StatsTraceContext;
import io.grpc.internal.WritableBuffer;

/**
 * Server stream for a Netty HTTP2 transport. Must only be called from the sending application
 * thread.
 */
class JettyServerStream extends AbstractServerStream {
  private static final Logger log = Logger.getLogger(JettyServerStream.class.getName());

  private final Sink sink = new Sink();
  private final TransportState state;
  private final Attributes attributes;

  public JettyServerStream(TransportState state, Attributes transportAttrs, StatsTraceContext statsTraceCtx) {
    super(new JettyWritableBufferAllocator(), statsTraceCtx);
    this.state = checkNotNull(state, "transportState");
    this.attributes = checkNotNull(transportAttrs);
  }

  @Override
  protected TransportState transportState() {
    return state;
  }

  @Override
  protected Sink abstractServerStreamSink() {
    return sink;
  }

  @Override
  public Attributes attributes() {
    return attributes;
  }

  @Override
  public void setListener(ServerStreamListener serverStreamListener) {
    state.setListener(serverStreamListener);
  }

  private class Sink implements AbstractServerStream.Sink {

    @Override
    public void writeHeaders(Metadata headers) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void writeFrame(WritableBuffer frame, boolean flush) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public void writeTrailers(Metadata trailers, boolean headersSent) {
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
  public static class TransportState extends AbstractServerStream.TransportState {

    protected TransportState(int maxMessageSize, StatsTraceContext statsTraceCtx) {
      super(maxMessageSize, statsTraceCtx);
      // TODO Auto-generated constructor stub
    }

    @Override
    public void bytesRead(int numBytes) {
      // TODO Auto-generated method stub
      
    }

    @Override
    protected void deframeFailed(Throwable cause) {
      // TODO Auto-generated method stub
      
    }}
}
