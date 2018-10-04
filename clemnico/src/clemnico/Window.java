package clemnico;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Window extends JFrame {

	//// Attributs////
	public Panel panel;
	private JLabel statusBar;
	private int fps;
	protected ImageLoader loader = new ImageLoader();

	// Objets de la fenêtre
	Player player = new Player(400, 200, 100, 100,"Player1", 0, 6);
	Portal portal1 = new Portal(-500, -500, 100, 20);
	Portal portal2 = new Portal(-500, -500, 100, 20);
	Obstacle obstacle = new Obstacle(300, 600, 600, 200, 0);
	Obstacle obstacle2 = new Obstacle(900, 550, 200, 247, 0);
	Obstacle obstacle3 = new Obstacle(150, 500, 200, 200, 0);
	Obstacle obstacle4 = new Obstacle(120, 200, 200, 200, 0);
	Obstacle[] obstacles = { obstacle, obstacle2, obstacle3, obstacle4 };
	ArrayList<Projectile> projectiles=player.getProjectiles();
	int projectileCount=0;

	GeneralEnemy enemy = new GeneralEnemy(400, 400, 50, 50, "Enemy1", 0, 5,false);
	FC fc = new FC();

	//// Constructeur////
	public Window(int fps) {
		this.fps = fps;

		this.setTitle("Ma fenetre");
		this.setSize(1500, 1000);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(true);

		portal1.getForm().setColor(Color.BLUE);
		portal2.getForm().setColor(Color.ORANGE);

		initPanel();
		stepGame(player, enemy);

	}

	//// Methodes ////

	private void initPanel() {
		panel = new Panel();
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.CENTER);

		statusBar = new JLabel("default");
		add(statusBar, BorderLayout.SOUTH);

		Handlerclass handler = new Handlerclass(panel, statusBar, player, portal1, portal2, obstacles);
		panel.addMouseListener(handler);
		panel.addMouseMotionListener(handler);
		addKeyListener(handler);

		// Animations
		Animation animation= createAnimation(Animations.AnimationPlayerDefault,player.getWidth(),player.getHeight());
		player.addAnimation(NameAnimation.DEFAULT,animation);
		player.addAnimation(NameAnimation.JUMPL,createAnimation(Animations.AnimationPlayerAirKick,player.getWidth(),player.getHeight()));
		player.addAnimation(NameAnimation.JUMPR,createAnimation(Animations.AnimationPlayerAirKick,player.getWidth(),player.getHeight()));
		player.addAnimation(NameAnimation.WALKL,createAnimation(Animations.AnimationPlayerWalkL,player.getWidth(),player.getHeight()));
		player.addAnimation(NameAnimation.WALKR,createAnimation(Animations.AnimationPlayerWalkR,player.getWidth(),player.getHeight()));
		player.addAnimation(NameAnimation.FALLL,createAnimation(Animations.AnimationPlayerAirKick,player.getWidth(),player.getHeight()));
		player.addAnimation(NameAnimation.FALLR,createAnimation(Animations.AnimationPlayerSpin,player.getWidth(),player.getHeight()));

		portal1.addAnimation(NameAnimation.DEFAULT,createAnimation(Animations.AnimationPortal1Default,portal1.getWidth(),portal1.getHeight()));
		portal2.addAnimation(NameAnimation.DEFAULT,createAnimation(Animations.AnimationPortal2Default,portal2.getWidth(),portal2.getHeight()));
		obstacle.addAnimation(NameAnimation.DEFAULT,createAnimation(Animations.AnimationObsatcleDefault2,obstacle.getWidth(),obstacle.getHeight()));
		obstacle2.addAnimation(NameAnimation.DEFAULT,createAnimation(Animations.AnimationObsatcleDefault2,obstacle2.getWidth(),obstacle2.getHeight()));
		obstacle3.addAnimation(NameAnimation.DEFAULT,createAnimation(Animations.AnimationObsatcleDefault2,obstacle3.getWidth(),obstacle3.getHeight()));
		obstacle4.addAnimation(NameAnimation.DEFAULT,createAnimation(Animations.AnimationObsatcleDefault2,obstacle3.getWidth(),obstacle3.getHeight()));
				
		enemy.addAnimation(NameAnimation.DEFAULT,createAnimation(Animations.AnimationPlayerDefault,enemy.getWidth(),enemy.getHeight()));
		enemy.addAnimation(NameAnimation.WALKL,createAnimation(Animations.AnimationPlayerDefault,enemy.getWidth(),enemy.getHeight()));
		enemy.addAnimation(NameAnimation.WALKR,createAnimation(Animations.AnimationPlayerDefault,enemy.getWidth(),enemy.getHeight()));
		enemy.addAnimation(NameAnimation.FALLL,createAnimation(Animations.AnimationPlayerDefault,enemy.getWidth(),enemy.getHeight()));
		enemy.addAnimation(NameAnimation.FALLR,createAnimation(Animations.AnimationPlayerDefault,enemy.getWidth(),enemy.getHeight()));
		enemy.addAnimation(NameAnimation.JUMPL,createAnimation(Animations.AnimationPlayerDefault,enemy.getWidth(),enemy.getHeight()));
		enemy.addAnimation(NameAnimation.JUMPR,createAnimation(Animations.AnimationPlayerDefault,enemy.getWidth(),enemy.getHeight()));
				
		player.setCurrentAnimation(NameAnimation.DEFAULT);
		portal1.setCurrentAnimation(NameAnimation.DEFAULT);
		portal2.setCurrentAnimation(NameAnimation.DEFAULT);
		obstacle.setCurrentAnimation(NameAnimation.DEFAULT);
		obstacle2.setCurrentAnimation(NameAnimation.DEFAULT);
		obstacle3.setCurrentAnimation(NameAnimation.DEFAULT);
		obstacle4.setCurrentAnimation(NameAnimation.DEFAULT);
		
		enemy.setCurrentAnimation(NameAnimation.DEFAULT);
		
		ArrayList<Entity> array = new ArrayList<Entity>();
		array.add(obstacle);
		array.add(obstacle2);
		array.add(obstacle3);
		array.add(obstacle4);
		array.add(player);
		array.add(portal1);
		array.add(portal2);
		
		array.add(enemy);
				
		panel.setEntityList(array);
		
	}
	
	private void refreshPanel() {
		
		projectiles=player.getProjectiles();
		ArrayList<Entity> array = panel.getEntityList();
		
		//Si un projectile a été rajouté par le joueur en faisant espace
		if (projectileCount<projectiles.size()) {			
			projectiles.get(projectileCount-1).addAnimation(NameAnimation.DEFAULT,createAnimation(Animations.AnimationObsatcleDefault2,projectiles.get(projectileCount-1).getWidth(),projectiles.get(projectileCount-1).getHeight()));
			projectiles.get(projectileCount-1).setCurrentAnimation(NameAnimation.DEFAULT);
			array.add(projectiles.get(projectileCount-1));
			projectileCount+=1;
			panel.setEntityList(array);
		}
	}

	private void stepGame(Player player, GeneralEnemy enemy) {

		Timer chrono = new Timer();
		int delay = 100;
		int period = 1000 / this.fps;

		chrono.schedule(new TimerTask() {
			long time = 0;

			@Override
			public void run() {
				
				time = time + 1;
				player.step(period);
				enemy.step(period);
				
				refreshPanel();
				for (Projectile projectile : projectiles) {
					projectile.step(period,player,1,1);
				}

				// Gestion portails teleportations
				player.portalInteraction(fc, portal1, portal2);
				enemy.portalInteraction(fc, portal1, portal2);
//				System.out.println(player.getX()+" "+player.isInTheAir());

				// Gestion obstacle
				player.obstacleInteraction2(fc, obstacles);
				enemy.obstacleInteraction2(fc, obstacles);
//				System.out.println(player.getForm().getX()+"   "+player.getForm().getY()+"   "+player.getTimeInAir());

				player.getCurrentAnimation().update();					
				
				panel.repaint();
			}
				
			}, delay, period);
	}

	
	
	
	
	
	public Animation createAnimation(Animations enumAnim, int width,int height) {
		int[][] listeArg =enumAnim.getListeArg();
		int[][] listeArg2 =new int[listeArg.length][listeArg[0].length+2];
		for(int i=0;i<listeArg.length;i++) {
			listeArg2[i][0]=listeArg[i][0];
			listeArg2[i][1]=listeArg[i][1];
			listeArg2[i][2]=listeArg[i][2];
			listeArg2[i][3]=listeArg[i][3];
			listeArg2[i][4]=width;
			listeArg2[i][5]=height;
			listeArg2[i][6]=listeArg[i][4];
		}
		return createAnimationGeneral(enumAnim.getPath(), enumAnim.getDef(), listeArg2,enumAnim.getListeTime());	
	}
	
	
	public Animation createAnimationGeneral(String path, int def, int[][] liste_arg, int[] listeTime) {
		BufferedImage img = this.loader.loadImage(path);
		SpriteSheet ss = new SpriteSheet(img, def);
		int nbSprite = liste_arg.length;
		Sprite[] spriteTab = new Sprite[nbSprite];
		for (int i = 0; i < nbSprite; i++) {
			Sprite sp = new Sprite(ss, liste_arg[i][0], liste_arg[i][1], liste_arg[i][2], liste_arg[i][3],
					liste_arg[i][4], liste_arg[i][5], (double) liste_arg[i][6]);
			spriteTab[i] = sp;
		}
		return new Animation(spriteTab, listeTime);

	}

	////////////////////////////////
	/////// GETTER AND SETTER //////
	////////////////////////////////

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}
}
