package org.opendaylight.openflowjava.nx.codec.action;

import java.nio.charset.Charset;

import org.opendaylight.openflowjava.nx.api.NiciraActionDeserializerKey;
import org.opendaylight.openflowjava.nx.api.NiciraActionSerializerKey;
import org.opendaylight.openflowjava.protocol.api.util.EncodeConstants;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowjava.nx.action.rev140421.action.container.action.choice.ActionSetNextHop;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowjava.nx.action.rev140421.action.container.action.choice.ActionSetNextHopBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.openflowjava.nx.action.rev140421.ofj.nx.action.set.next.hop.grouping.NxSetNextHopBuilder;

import io.netty.buffer.ByteBuf;

public class SetNextHopCodec extends AbstractActionCodec{
	
	public static final int LENGTH = 24;		//咋算呢
    public static final byte SUBTYPE = 45;	//乱写的 
    public static final NiciraActionSerializerKey SERIALIZER_KEY = new NiciraActionSerializerKey(
            EncodeConstants.OF13_VERSION_ID, ActionSetNextHop.class);
    public static final NiciraActionDeserializerKey DESERIALIZER_KEY = new NiciraActionDeserializerKey(
            EncodeConstants.OF13_VERSION_ID, SUBTYPE);

	@Override
	public void serialize(Action input, ByteBuf outBuffer) {
		ActionSetNextHop actionSetNextHop = (ActionSetNextHop) input.getActionChoice();
		serializeHeader(LENGTH, SUBTYPE, outBuffer);
		byte[] src = actionSetNextHop.getNxSetNextHop().getNextHop().getBytes();
		outBuffer.writeBytes(src);
	}

	@Override
	public Action deserialize(ByteBuf message) {
		ActionBuilder actionBuilder = deserializeHeader(message);
		NxSetNextHopBuilder nxSetNextHopBuilder = new NxSetNextHopBuilder();
		ActionSetNextHopBuilder actionSetNextHopBuilder = new ActionSetNextHopBuilder();
		nxSetNextHopBuilder.setNextHop(message.toString(Charset.defaultCharset()));
		actionSetNextHopBuilder.setNxSetNextHop(nxSetNextHopBuilder.build());
		actionBuilder.setActionChoice(actionSetNextHopBuilder.build());
		return actionBuilder.build();
	}

}
