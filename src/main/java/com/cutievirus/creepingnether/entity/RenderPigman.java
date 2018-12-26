package com.cutievirus.creepingnether.entity;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;

public class RenderPigman extends RenderBiped<EntityPigman> {

	private static final ResourceLocation PIGMAN_TEXTURE = new ResourceLocation("creepingnether:textures/entity/pigman.png");

	public RenderPigman(RenderManager renderManagerIn) {
		super(renderManagerIn, new ModelModBiped(), 0.5F);
        this.addLayer(new LayerBipedArmor(this) {
            @Override
			protected void initArmor() {
                modelLeggings = new ModelModBiped(0.5F);
                modelArmor = new ModelModBiped(1.0F);
            }
        });
	}

    @Override
	protected ResourceLocation getEntityTexture(EntityPigman entity) {
        return PIGMAN_TEXTURE;
    }

}
