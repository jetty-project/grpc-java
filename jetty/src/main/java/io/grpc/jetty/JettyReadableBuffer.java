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

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import io.grpc.internal.AbstractReadableBuffer;
import io.grpc.internal.ReadableBuffer;
import io.netty.buffer.ByteBuf;

/**
 * A {@link java.nio.Buffer} implementation that is backed by a Netty {@link ByteBuf}. This class
 * does not call {@link ByteBuf#retain}, so if that is needed it should be called prior to creating
 * this buffer.
 */
class JettyReadableBuffer extends AbstractReadableBuffer {

  @Override
  public int readableBytes() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int readUnsignedByte() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public void skipBytes(int length) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void readBytes(byte[] dest, int destOffset, int length) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void readBytes(ByteBuffer dest) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void readBytes(OutputStream dest, int length) throws IOException {
    // TODO Auto-generated method stub
    
  }

  @Override
  public ReadableBuffer readBytes(int length) {
    // TODO Auto-generated method stub
    return null;
  }
}
