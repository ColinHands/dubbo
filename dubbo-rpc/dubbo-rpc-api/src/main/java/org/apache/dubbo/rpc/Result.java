/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.rpc;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiConsumer;
import java.util.function.Function;


/**
 * (API, Prototype, NonThreadSafe)
 *
 * An RPC {@link Result}.
 *
 * Known implementations are:
 * 1. {@link AsyncRpcResult}, it's a {@link CompletionStage} whose underlying value signifies the return value of an RPC call.
 * 2. {@link AppResponse}, it inevitably inherits {@link CompletionStage} and {@link Future}, but you should never treat AppResponse as a type of Future,
 *    instead, it is a normal concrete type.
 *
 * @serial Don't change the class name and package name.
 * @see org.apache.dubbo.rpc.Invoker#invoke(Invocation)
 * @see AppResponse
 */
public interface Result extends Serializable {

    /**
     * Get invoke result.
     *
     * @return result. if no result return null.
     */
    Object getValue();

    void setValue(Object value);

    /**
     * Get exception.
     *
     * @return exception. if no exception return null.
     */
    Throwable getException();

    void setException(Throwable t);

    /**
     * Has exception.
     *
     * @return has exception.
     */
    boolean hasException();

    /**
     * Recreate.
     * 重新创建
     * <p>
     * <code>
     * if (hasException()) {
     * throw getException();
     * } else {
     * return getValue();
     * }
     * </code>
     *
     * @return result.
     * @throws if has exception throw it.
     */
    Object recreate() throws Throwable;

    /**
     * get attachments.
     *
     * @return attachments.
     */
    Map<String, Object> getAttachments();

    /**
     * Add the specified map to existing attachments in this instance.
     *
     * @param map
     */
    void addAttachments(Map<String, Object> map);

    /**
     * Replace the existing attachments with the specified param.
     *
     * @param map
     */
    void setAttachments(Map<String, Object> map);

    /**
     * get attachment by key.
     *
     * @return attachment value.
     */
    Object getAttachment(String key);

    /**
     * get attachment by key with default value.
     *
     * @return attachment value.
     */
    Object getAttachment(String key, Object defaultValue);

    void setAttachment(String key, Object value);

    /**
     * Add a callback which can be triggered when the RPC call finishes.
     * <p>
     * Just as the method name implies, this method will guarantee the callback being triggered under the same context as when the call was started,
     * see implementation in {@link Result#whenCompleteWithContext(BiConsumer)}
     *
     * 添加一个回调，它可以在RPC调用结束时触发。
     * 正如方法名称所暗示的，这个方法将保证在调用开始时的相同上下文中触发回调，参见{@link Result#when completewithcontext (BiConsumer)}中的实现。
     * @param fn
     * @return
     */
    Result whenCompleteWithContext(BiConsumer<Result, Throwable> fn);

    <U> CompletableFuture<U> thenApply(Function<Result, ? extends U> fn);

    Result get() throws InterruptedException, ExecutionException;

    Result get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException;
}