package org.openpixi.pixi.physics.particles;

/**
 * This particle class is used for CGC simulations where the particles move in a certain direction along grid lines
 * without being affected by a force. The color charge is parallel transported along the fixed trajectory.
 */
public class CGCParticle extends YangMillsParticle {

	public int direction;

	public CGCParticle(int numberOfDimensions, int numberOfColors, int direction) {
		super(numberOfDimensions, numberOfColors);
		this.direction = direction;
	}

	public IParticle copy() {
		CGCParticle p = new CGCParticle(this.numberOfDimensions, this.numberOfColors, this.direction);

		for (int i = 0; i < this.numberOfDimensions; i++) {
			p.pos0[i] = this.pos0[i];
			p.pos1[i] = this.pos1[i];
			p.vel[i] = this.vel[i];
			p.acc[i] = this.acc[i];
		}

		p.setRadius(this.r);
		p.setDisplayColor(this.col);

		return p;
	}

}
