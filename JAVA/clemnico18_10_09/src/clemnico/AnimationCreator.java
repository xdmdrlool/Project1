package clemnico;


public class AnimationCreator {
	
	

	
	public AnimationCreator() {
		// TODO Auto-generated constructor stub
	}
	
	public Animation createAnimation(Animations enumAnim, int width,int height) {
		int[][] listeArg =enumAnim.getListeArg();
		SpriteSheet ss = new SpriteSheet(enumAnim.getImage(), enumAnim.getDef());
		int nbSprite = listeArg.length;
		Sprite[] spriteTab = new Sprite[nbSprite];
		for (int i = 0; i < nbSprite; i++) {
			Sprite sp = new Sprite(ss, listeArg[i][0], listeArg[i][1], listeArg[i][2], listeArg[i][3],
					width,height, (double) listeArg[i][4]);
			spriteTab[i] = sp;
		}
		return new Animation(spriteTab, enumAnim.getListeTime());
	}
	
	

	
	
}
