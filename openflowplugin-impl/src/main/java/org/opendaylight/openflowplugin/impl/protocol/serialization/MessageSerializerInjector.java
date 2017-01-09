/*
 * Copyright (c) 2016 Pantheon Technologies s.r.o. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.openflowplugin.impl.protocol.serialization;

import com.google.common.annotations.VisibleForTesting;
import java.util.function.Consumer;
import java.util.function.Function;
import org.opendaylight.openflowjava.protocol.api.extensibility.OFSerializer;
import org.opendaylight.openflowjava.protocol.api.extensibility.SerializerExtensionProvider;
import org.opendaylight.openflowjava.protocol.api.keys.MessageTypeKey;
import org.opendaylight.openflowjava.protocol.api.util.EncodeConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.protocol.rev130731.OfHeader;

/**
 * Util class for injecting new message serializers into OpenflowJava
 */
class MessageSerializerInjector {

    /**
     * Injects message serializers into provided {@link org.opendaylight.openflowjava.protocol.api.extensibility.SerializerExtensionProvider}
     * @param provider OpenflowJava serializer extension provider
     */
    static void injectSerializers(final SerializerExtensionProvider provider) {
        // Inject new message serializers here using injector created by createInjector method
        final Function<Class<?>, Consumer<OFSerializer<? extends OfHeader>>> injector =
                createInjector(provider, EncodeConstants.OF13_VERSION_ID);
    }

    /**
     * Create injector that will inject new serializers into #{@link org.opendaylight.openflowjava.protocol.api.extensibility.SerializerExtensionProvider}
     * @param provider OpenflowJava serializer extension provider
     * @param version Openflow version
     * @return injector
     */
    @VisibleForTesting
    static Function<Class<?>, Consumer<OFSerializer<? extends OfHeader>>> createInjector(final SerializerExtensionProvider provider,
                                                                                                 final byte version) {
        return type -> serializer ->
                provider.registerSerializer(
                        new MessageTypeKey<>(version, type),
                        serializer);
    }

}
