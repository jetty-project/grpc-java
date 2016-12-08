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

import static com.google.common.base.Preconditions.checkArgument;
import static io.grpc.internal.GrpcUtil.DEFAULT_MAX_MESSAGE_SIZE;

import java.io.File;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

import io.grpc.ExperimentalApi;
import io.grpc.internal.AbstractServerImplBuilder;
import io.grpc.internal.GrpcUtil;

/**
 * A builder to help simplify the construction of a Netty-based GRPC server.
 */
@ExperimentalApi("https://github.com/grpc/grpc-java/issues/1784")
public final class JettyServerBuilder extends AbstractServerImplBuilder<JettyServerBuilder> {
  public static final int DEFAULT_FLOW_CONTROL_WINDOW = 1048576; // 1MiB

  private final SocketAddress address;
  private int maxConcurrentCallsPerConnection = Integer.MAX_VALUE;
  private int flowControlWindow = DEFAULT_FLOW_CONTROL_WINDOW;
  private int maxMessageSize = DEFAULT_MAX_MESSAGE_SIZE;
  private int maxHeaderListSize = GrpcUtil.DEFAULT_MAX_HEADER_LIST_SIZE;

  /**
   * Creates a server builder that will bind to the given port.
   *
   * @param port the port on which the server is to be bound.
   * @return the server builder.
   */
  public static JettyServerBuilder forPort(int port) {
    return new JettyServerBuilder(port);
  }

  /**
   * Creates a server builder configured with the given {@link SocketAddress}.
   *
   * @param address the socket address on which the server is to be bound.
   * @return the server builder
   */
  public static JettyServerBuilder forAddress(SocketAddress address) {
    return new JettyServerBuilder(address);
  }

  private JettyServerBuilder(int port) {
    this.address = new InetSocketAddress(port);
  }

  private JettyServerBuilder(SocketAddress address) {
    this.address = address;
  }


  /**
   * Sets the TLS context to use for encryption. Providing a context enables encryption. It must
   * have been configured with {@link GrpcSslContexts}, but options could have been overridden.
   */
  public JettyServerBuilder sslContext(Object sslContext) {
    // TODO
    return this;
  }

  /**
   * The maximum number of concurrent calls permitted for each incoming connection. Defaults to no
   * limit.
   */
  public JettyServerBuilder maxConcurrentCallsPerConnection(int maxCalls) {
    checkArgument(maxCalls > 0, "max must be positive: %s", maxCalls);
    this.maxConcurrentCallsPerConnection = maxCalls;
    return this;
  }

  /**
   * Sets the HTTP/2 flow control window. If not called, the default value
   * is {@link #DEFAULT_FLOW_CONTROL_WINDOW}).
   */
  public JettyServerBuilder flowControlWindow(int flowControlWindow) {
    checkArgument(flowControlWindow > 0, "flowControlWindow must be positive");
    this.flowControlWindow = flowControlWindow;
    return this;
  }

  /**
   * Sets the maximum message size allowed to be received on the server. If not called,
   * defaults to 4 MiB. The default provides protection to services who haven't considered the
   * possibility of receiving large messages while trying to be large enough to not be hit in normal
   * usage.
   */
  public JettyServerBuilder maxMessageSize(int maxMessageSize) {
    checkArgument(maxMessageSize >= 0, "maxMessageSize must be >= 0");
    this.maxMessageSize = maxMessageSize;
    return this;
  }

  /**
   * Sets the maximum size of header list allowed to be received on the server. If not called,
   * defaults to {@link GrpcUtil#DEFAULT_MAX_HEADER_LIST_SIZE}.
   */
  public JettyServerBuilder maxHeaderListSize(int maxHeaderListSize) {
    checkArgument(maxHeaderListSize > 0, "maxHeaderListSize must be > 0");
    this.maxHeaderListSize = maxHeaderListSize;
    return this;
  }

  @Override
  protected JettyServer buildTransportServer() {
    return new JettyServer(address, maxConcurrentCallsPerConnection, flowControlWindow, maxMessageSize,
        maxHeaderListSize);
  }

  @Override
  public JettyServerBuilder useTransportSecurity(File certChain, File privateKey) {
    // TODO
    return this;
  }
}
