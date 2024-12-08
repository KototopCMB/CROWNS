package com.rae.crowns.client.rendering.renderer;

import com.jozufozu.flywheel.backend.Backend;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.rae.crowns.init.PartialModelInit;
import com.rae.crowns.content.thermodynamics.turbine.TurbineStageBlockEntity;
import com.rae.crowns.content.thermodynamics.turbine.TurbineStageBlock;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class TurbineStageRenderer extends KineticBlockEntityRenderer<TurbineStageBlockEntity> {
    public TurbineStageRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
    @Override
    protected void renderSafe(TurbineStageBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer,
                              int light, int overlay) {
        if (Backend.canUseInstancing(be.getLevel())) return;

        //super.renderSafe(be, partialTicks, ms, buffer, light, overlay);
        BlockState state = be.getBlockState();

        Direction direction =  Direction.fromAxisAndDirection(((TurbineStageBlock)state.getBlock()).getRotationAxis(state), Direction.AxisDirection.POSITIVE);
        VertexConsumer vb = buffer.getBuffer(RenderType.cutoutMipped());
        ms.pushPose();
        SuperByteBuffer memoryRoll =
                CachedBufferer.partialFacing(PartialModelInit.TURBINE_STAGE, be.getBlockState(), direction.getOpposite());
        standardKineticRotationTransform(memoryRoll, be, light).renderInto(ms, vb);
        ms.popPose();
    }
}
