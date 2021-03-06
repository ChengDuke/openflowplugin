/*
 * Copyright (c) 2016 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.openflowplugin.openflow.md.core.sal.convertor.match.cases;

import java.util.Optional;
import javax.annotation.Nonnull;
import org.opendaylight.openflowplugin.api.OFConstants;
import org.opendaylight.openflowplugin.openflow.md.core.sal.convertor.ConvertorExecutor;
import org.opendaylight.openflowplugin.openflow.md.core.sal.convertor.common.ConvertorCase;
import org.opendaylight.openflowplugin.openflow.md.core.sal.convertor.match.data.MatchResponseConvertorData;
import org.opendaylight.openflowplugin.openflow.md.util.ByteUtil;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.ProtocolMatchFieldsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.protocol.match.fields.PbbBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entry.value.grouping.match.entry.value.PbbIsidCase;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.oxm.rev150225.match.entry.value.grouping.match.entry.value.pbb.isid._case.PbbIsid;

public class OfToSalPbbIsidCase extends ConvertorCase<PbbIsidCase, MatchBuilder, MatchResponseConvertorData> {
    public OfToSalPbbIsidCase() {
        super(PbbIsidCase.class, true, OFConstants.OFP_VERSION_1_0, OFConstants.OFP_VERSION_1_3);
    }

    @Override
    public Optional<MatchBuilder> process(@Nonnull PbbIsidCase source, MatchResponseConvertorData data, ConvertorExecutor convertorExecutor) {
        final MatchBuilder matchBuilder = data.getMatchBuilder();
        final ProtocolMatchFieldsBuilder protocolMatchFieldsBuilder = data.getProtocolMatchFieldsBuilder();

        PbbIsid pbbIsid = source.getPbbIsid();

        if (pbbIsid != null) {
            PbbBuilder pbbBuilder = new PbbBuilder();
            pbbBuilder.setPbbIsid(pbbIsid.getIsid());
            byte[] mask = pbbIsid.getMask();

            if (mask != null) {
                pbbBuilder.setPbbMask(ByteUtil.bytesToUnsignedMedium(mask));
            }

            protocolMatchFieldsBuilder.setPbb(pbbBuilder.build());
            matchBuilder.setProtocolMatchFields(protocolMatchFieldsBuilder.build());
        }

        return Optional.of(matchBuilder);
    }
}
