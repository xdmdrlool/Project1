package clemnico;

public enum Animations {

	// path= "NomDeLaSpriteSheet.png" 
	// def = taille du carreau de la grille de la SpriteSheet
	// listeArg= {  { co lonne , ligne ,  wSprite(default=def) ,hSprite(default=def) ,  angle } ,  ...    }
	
	
	
	AnimationPlayerDefault("SpriteSheet_test.png" , 64 , new int[][]  { { 0 , 0 , 64 , 64 , 0 } }   ),
	
	AnimationPlayerDefault2("SpriteSheet_Player3.png" , 64 , new int[][]  { { 0 , 0 , 19 , 19 , 0 } , { 1 , 0 , 19 , 19 , 0 } }    ),
	
	AnimationPlayerAirKick("SpriteSheet_Player_AirKick.png" , 64 , new int[][]  { { 0 , 0 , 36 , 50 , 0 }  }    ),
	
	AnimationPlayerSpin("SpriteSheet_Player_Spin.png" , 64 , new int[][]  { { 0 , 0 , 32 , 32 , 0 } , { 1 , 0 , 32 , 32 , 0 } , { 2 , 0 , 32 , 32 , 0 } , { 3 , 0 , 32 , 32 , 0 } }    ),
	
	AnimationPlayerWalk("SpriteSheet_Player2.png" , 64 , new int[][]  { { 0 , 0 , 25 , 54 , 0 }, { 1 , 0 , 25 , 54 , 0 } }   ),

	
	
	
	AnimationPortal1Default("SpriteSheet_Portal.png", 64 , new int[][] { { 0 , 0 , 61 , 10 , 0 } } ),
	
	AnimationPortal2Default("SpriteSheet_Portal.png", 64 , new int[][] { { 1 , 0 , 61 , 10 , 0 } } ),

	
	
	
	AnimationObsatcleDefault("SpriteSheet_Obstacle.png",64, new int[][] { {0 , 0 , 58 , 14 , 0 } });
	
	
	

	
	
	

	
	private String path;
	private int def;
	private int[][] listeArg;

	
	
	
	Animations(String path, int def,int[][] listeArg){
		this.setPath(path);
		this.setDef(def);
		this.setListeArg(listeArg);
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
}
