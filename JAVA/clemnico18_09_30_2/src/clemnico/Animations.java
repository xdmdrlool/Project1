package clemnico;

public enum Animations {
 
	// path= "NomDeLaSpriteSheet.png" 
	// def = taille du carreau de la grille de la SpriteSheet
	// listeArg= {  { colonne , ligne ,  wSprite(default=def) ,hSprite(default=def) ,  angle } ,  ...    }
	
	AnimationTest("SpriteSheet_test.png" , 64 , new int[][]  { { 0 , 0 , 64 , 64 , 0 } } ,  new int[] {1}   ),
	
	AnimationPlayerDefault("SpriteSheet_test.png" , 64 , new int[][]  { { 0 , 0 , 64 , 64 , 0 } }   ,  new int[] {1}  ),
	
	AnimationPlayerDefault2("SpriteSheet_Player3.png" , 64 , new int[][]  { { 0 , 0 , 19 , 19 , 0 } , { 1 , 0 , 19 , 19 , 0 } }, new int[] {1,1}    ),
	
	AnimationPlayerAirKick("SpriteSheet_Player_AirKick.png" , 64 , new int[][]  { { 0 , 0 , 36 , 50 , 0 }  } ,new int[] {1}   ),
	
	AnimationPlayerSpin("SpriteSheet_Player_Spin.png" , 64 , new int[][]  { { 0 , 0 , 32 , 32 , 0 } , { 1 , 0 , 32 , 32 , 0 } , { 2 , 0 , 32 , 32 , 0 } , { 3 , 0 , 32 , 32 , 0 } } , new int[] {10,10,10,10}   ),
	
	AnimationPlayerWalk("SpriteSheet_Player2.png" , 64 , new int[][]  { { 0 , 0 , 25 , 54 , 0 }, { 1 , 0 , 25 , 54 , 0 } },new int[] {1,1}   ),

	
	
	
	AnimationPortal1Default("SpriteSheet_Portal.png", 64 , new int[][] { { 0 , 0 , 61 , 10 , 0 } },new int[] {1} ),
	
	AnimationPortal2Default("SpriteSheet_Portal.png", 64 , new int[][] { { 1 , 0 , 61 , 10 , 0 } },new int[] {1} ),

	
	
	
	AnimationObsatcleDefault("SpriteSheet_Obstacle.png",64, new int[][] { {0 , 0 , 58 , 14 , 0 } },new int[] {1}),
	
	AnimationObsatcleDefault2("SpriteSheet_Obstacle2.png",64, new int[][] { {0 , 0 , 51 , 51 , 0 } },new int[] {1});
	
	
	
	;
	
	
	

	
	
	

	
	private String path;
	private int def;
	private int[][] listeArg;
	private int[] listeTime;

	
	
	
	Animations(String path, int def,int[][] listeArg,int[] listeTime){
		this.setPath(path);
		this.setDef(def);
		this.setListeArg(listeArg);
		this.setListeTime(listeTime);
	}

	
	
	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int[][] getListeArg() {
		return listeArg;
	}

	public void setListeArg(int[][] listeArg) {
		this.listeArg = listeArg;
	}



	public int[] getListeTime() {
		return listeTime;
	}



	public void setListeTime(int[] listeTime) {
		this.listeTime = listeTime;
	}
}
