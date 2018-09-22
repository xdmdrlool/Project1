package clemnico;

public enum Animations {

	// path= "NomDeLaSpriteSheet.png" 
	// def = taille du carreau de la grille de la SpriteSheet
	// listeArg= {  { colonne , ligne ,  wSprite(default=def) ,hSprite(default=def) ,  angle } ,  ...    }
	
	
	AnimationPlayerDefault("SpriteSheet_Player.png" , 64 , new int[][]  { { 0 , 0 , 64 , 64 , 0 } }   ),
	
	AnimationPortal1Default("SpriteSheet_Portal.png", 64 , new int[][] { { 0 , 0 , 61 , 10 , 0 } } ),
	
	AnimationPortal2Default("SpriteSheet_Portal.png", 64 , new int[][] { { 1 , 0 , 61 , 10 , 0 } } ),
	
	AnimationObsatcleDefault("SpriteSheet_test_Obstacle2.png",64, new int[][] { {0 , 0 , 1 , 1 , 0 } });
	
	
	
	

	
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
