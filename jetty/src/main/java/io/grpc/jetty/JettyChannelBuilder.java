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

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import javax.annotation.Nullable;

import io.grpc.Attributes;
import io.grpc.ExperimentalApi;
import io.grpc.Internal;
import io.grpc.NameResolver;
import io.grpc.internal.AbstractManagedChannelImplBuilder;
import io.grpc.internal.ClientTransportFactory;
import io.grpc.internal.ConnectionClientTransport;
import io.grpc.internal.GrpcUtil;

/**
 * A builder to help simplify construction of channels using the Netty transport.
 */
@ExperimentalApi("https://github.com/grpc/grpc-java/issues/1784")
public class JettyChannelBuilder extends AbstractManagedChannelImplBuilder<JettyChannelBuilder> {
  public static final int DEFAULT_FLOW_CONTROL_WINDOW = 1048576; // 1MiB

  private NegotiationType negotiationType = NegotiationType.TLS;

  @Nullable
  private int flowControlWindow = DEFAULT_FLOW_CONTROL_WINDOW;
  private int maxHeaderListSize = GrpcUtil.DEFAULT_MAX_HEADER_LIST_SIZE;

private Object sslContext;

  /**
   * Creates a new builder with the given server address. This factory method is primarily intended
   * for using Netty Channel types other than SocketChannel. {@link #forAddress(String, int)} should
   * generally be preferred over this method, since that API permits delaying DNS lookups and
   * noticing changes to DNS.
   */
  public static JettyChannelBuilder forAddress(SocketAddress serverAddress) {
    return new JettyChannelBuilder(serverAddress);
  }

  /**
   * Creates a new builder with the given host and port.
   */
  public static JettyChannelBuilder forAddress(String host, int port) {
    return new JettyChannelBuilder(host, port);
  }

  /**
   * Creates a new builder with the given target string that will be resolved by
   * {@link io.grpc.NameResolver}.
   */
  public static JettyChannelBuilder forTarget(String target) {
    return new JettyChannelBuilder(target);
  }

  protected JettyChannelBuilder(String host, int port) {
    this(GrpcUtil.authorityFromHostAndPort(host, port));
  }

  protected JettyChannelBuilder(String target) {
    super(target);
  }

  protected JettyChannelBuilder(SocketAddress address) {
    super(address, getAuthorityFromAddress(address));
  }

  private static String getAuthorityFromAddress(SocketAddress address) {
    if (address instanceof InetSocketAddress) {
      InetSocketAddress inetAddress = (InetSocketAddress) address;
      return GrpcUtil.authorityFromHostAndPort(inetAddress.getHostString(), inetAddress.getPort());
    } else {
      return address.toString();
    }
  }

  /**
   * Sets the negotiation type for the HTTP/2 connection.
   *
   * <p>Default: <code>TLS</code>
   */
  public final JettyChannelBuilder negotiationType(NegotiationType type) {
    negotiationType = type;
    return this;
  }


  /**
   * SSL/TLS context to use instead of the system default. It must have been configured with {@link
   * GrpcSslContexts}, but options could have been overridden.
   */
  public final JettyChannelBuilder sslContext(Object sslContext) {
    if (sslContext != null) {
    }
    this.sslContext = sslContext;
    return this;
  }

  /**
   * Sets the flow control window in bytes. If not called, the default value
   * is {@link #DEFAULT_FLOW_CONTROL_WINDOW}).
   */
  public final JettyChannelBuilder flowControlWindow(int flowControlWindow) {
    checkArgument(flowControlWindow > 0, "flowControlWindow must be positive");
    this.flowControlWindow = flowControlWindow;
    return this;
  }

  /**
   * Sets the max message size.
   *
   * @deprecated Use {@link #maxInboundMessageSize} instead
   */
  @Deprecated
  public final JettyChannelBuilder maxMessageSize(int maxMessageSize) {
    maxInboundMessageSize(maxMessageSize);
    return this;
  }

  /**
   * Sets the maximum size of header list allowed to be received on the channel. If not called,
   * defaults to {@link GrpcUtil#DEFAULT_MAX_HEADER_LIST_SIZE}.
   */
  public final JettyChannelBuilder maxHeaderListSize(int maxHeaderListSize) {
    checkArgument(maxHeaderListSize > 0, "maxHeaderListSize must be > 0");
    this.maxHeaderListSize = maxHeaderListSize;
    return this;
  }

  /**
   * Equivalent to using {@link #negotiationType(NegotiationType)} with {@code PLAINTEXT} or
   * {@code PLAINTEXT_UPGRADE}.
   */
  @Override
  public final JettyChannelBuilder usePlaintext(boolean skipNegotiation) {
    if (skipNegotiation) {
      negotiationType(NegotiationType.PLAINTEXT);
    } else {
      negotiationType(NegotiationType.PLAINTEXT_UPGRADE);
    }
    return this;
  }

  @Override
  protected ClientTransportFactory buildTransportFactory() {
    return new JettyTransportFactory(
        negotiationType, sslContext, flowControlWindow, maxInboundMessageSize(), maxHeaderListSize);
  }

  @Override
  protected Attributes getNameResolverParams() {
    int defaultPort;
    switch (negotiationType) {
      case PLAINTEXT:
      case PLAINTEXT_UPGRADE:
        defaultPort = GrpcUtil.DEFAULT_PORT_PLAINTEXT;
        break;
      case TLS:
        defaultPort = GrpcUtil.DEFAULT_PORT_SSL;
        break;
      default:
        throw new AssertionError(negotiationType + " not handled");
    }
    return Attributes.newBuilder()
        .set(NameResolver.Factory.PARAMS_DEFAULT_PORT, defaultPort).build();
  }


  /**
   * Creates Netty transports. Exposed for internal use, as it should be private.
   */
  @Internal
  protected static final class JettyTransportFactory implements ClientTransportFactory {
    private final NegotiationType negotiationType;
    private final Object sslContext;
    private final int flowControlWindow;
    private final int maxMessageSize;
    private final int maxHeaderListSize;

    private boolean closed;

    private JettyTransportFactory(
        NegotiationType negotiationType, Object sslContext,
        int flowControlWindow, int maxMessageSize,
        int maxHeaderListSize) {
      this.negotiationType = negotiationType;
      this.sslContext = sslContext;
      this.flowControlWindow = flowControlWindow;
      this.maxMessageSize = maxMessageSize;
      this.maxHeaderListSize = maxHeaderListSize;
    }

    @Override
    public ConnectionClientTransport newClientTransport(
        SocketAddress serverAddress, String authority, @Nullable String userAgent) {
      if (closed) {
        throw new IllegalStateException("The transport factory is closed.");
      }
      return newClientTransport(serverAddress, authority, userAgent);
    }

    @Override
    public void close() {
      if (closed) {
        return;
      }
      closed = true;
    }
  }
}
