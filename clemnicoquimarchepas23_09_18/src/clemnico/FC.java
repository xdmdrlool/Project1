package clemnico;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Default;

public class FC {



	public FC() {
		// TODO Auto-generated constructor stub
	}
	
	
	public class  Box{
	
	  int x;
	  int y;
	  int w;
	  int h;
	};
	
	public class Cercle{
	   int x,y;
	   int radius;
	};

	public class Vecteur{
	  float x,y;
	};
	
	//Calcule la distance euclidienne entre deux points
	public double distance(Point A, Point B) {
		return Math.sqrt((A.x-B.x)*(A.x-B.x)+(A.y-B.y)*(A.y-B.y));
	}

	public float norm(Vecteur A) {
		return (float) Math.sqrt(A.x*A.x+A.y*A.y);
	}
	
	public void normalize(Vecteur A) {
		float r= norm(A);
		if (r!=0) {
		A.x= A.x/r;
		A.y= A.y/r;
		}
		
	}
	
	public Box RectDroit2Box(FormRect rect) {
		Box box=new Box();
		box.x=rect.getX();
		box.y=rect.getY();
		box.w=rect.getWidth();
		box.h=rect.getHeight();
		return box;
		
	}
	
	public Point[] Rect2Array(FormRect rect) {
		int x =rect.getX();
		int y =rect.getY();
		int w =rect.getWidth();
		int h =rect.getHeight();
		if (rect.getAngle()==0) {return (new Point[] {new Point(x,y),new Point(x+w,y),new Point(x+w,y+h),new Point(x,y+h)});}
		double angle = Math.toRadians(rect.getAngle());
		double x0=x+w/2;
		double y0=y+h/2;
		double r= Math.sqrt(h*h+w*w)/2;
		double alpha1=-Math.atan(h*1.0/w);
		double alpha2=-alpha1;
		double alpha3=Math.PI+alpha1;
		double alpha4=Math.PI+alpha2;
		Point[] t= {new Point(),new Point(),new Point(),new Point()};
		
		t[0].setLocation(new Point((int) (x0+r*Math.cos(angle+alpha4)),(int) (y0+r*Math.sin(angle+alpha4))));
		t[1].setLocation(new Point((int) (x0+r*Math.cos(angle+alpha1)),(int) (y0+r*Math.sin(angle+alpha1))));
		t[2].setLocation(new Point((int) (x0+r*Math.cos(angle+alpha2)),(int) (y0+r*Math.sin(angle+alpha2))));
		t[3].setLocation(new Point((int) (x0+r*Math.cos(angle+alpha3)),(int) (y0+r*Math.sin(angle+alpha3))));
		
		return t;
	}
	
	public Cercle Cirle2Cercle(FormCircle circle) {
		Cercle cercle=new Cercle();
		cercle.x=circle.getX();
		cercle.y=circle.getY();
		cercle.radius=circle.getRadius();
		return cercle;
	}
	
	public boolean Collision(Form form1, Form form2) {
		 if (form1.getType()=="RECT") {
			 FormRect rect1= new FormRect(Color.BLACK,form1.getX(),form1.getY(),form1.getWidth(),form1.getHeight(),form1.getAngle());
			 if (form2.getType()=="RECT") {
				 FormRect rect2= new FormRect(Color.BLACK,form2.getX(),form2.getY(),form2.getWidth(),form2.getHeight(),form2.getAngle());
				 return Collision(rect1,rect2); 
			 }
			 else {
				 FormCircle circle2=new FormCircle(Color.BLACK,form2.getX(),form2.getY(),form2.getRadius());
				 return Collision(rect1,circle2);
		 }
		 }
		 
		 else {
			 FormCircle circle1=new FormCircle(Color.BLACK,form1.getX(),form1.getY(),form1.getRadius());
			 if (form2.getType()=="RECT") {
				 FormRect rect2= new FormRect(Color.BLACK,form2.getX(),form2.getY(),form2.getWidth(),form2.getHeight(),form2.getAngle());
				 return Collision(circle1,rect2);
		 }
		 else {
			 FormCircle circle2=new FormCircle(Color.BLACK,form2.getX(),form2.getY(),form2.getRadius());
			 return Collision(circle1,circle2);
		 }

	}
	}
	
	public boolean Collision(FormRect rect1,FormRect rect2  ) {

		Point[] list1=Rect2Array(rect1);
		Point[] list2=Rect2Array(rect2);
		for (int i=0 ;i<=3;i++) {
		if (Collision(list1, 4, list2[i]) || Collision(list2, 4, list1[i]) )  {
			return true;
		}
		}
		return false;
	}
	
	public boolean Collision(FormRect rect,FormCircle circle) {
		Point[] t=Rect2Array(rect);
		Cercle cercle= Cirle2Cercle(circle);
		Point point =new Point();
		point.x=cercle.x;
		point.y=cercle.y;
		return Collision(t,4,point)
				|| CollisionSegment(t[0], t[1], cercle)
				|| CollisionSegment(t[1], t[2], cercle)
				|| CollisionSegment(t[2], t[3], cercle) 
				|| CollisionSegment(t[3], t[0], cercle);
	}
	
	public boolean Collision(FormCircle circle,FormRect rect) {
		return Collision(rect,circle);
	}
	
	public boolean Collision(FormCircle circle1,FormCircle circle2) {
		return Collision(Cirle2Cercle(circle1),Cirle2Cercle(circle2));
	}

	
	
	
	public Point calculIntersectionSeg(Point A, Point B, Point C, Point D) {
		
		float xIntersect=-1;
		float yIntersect=-1;
		
		if (A.x!=B.x && C.x!=D.x) {
			//Pentes des segments
			float a1=(float) ((A.y-B.y)*1./(A.x-B.x)*1.);
			float a2=(float) ((C.y-D.y)*1./(C.x-D.x)*1.);
			if (a1==a2) {return null;}
			//Ordonnees à l'origine
			float b1=A.y-a1*A.x;
			float b2=C.y-a2*C.x;
			xIntersect=(b2-b1)/(a1-a2);
			yIntersect=a1*xIntersect+b1;
		}
		else if(A.x==B.x && C.x!=D.x) {
			float a2=(float) ((C.y-D.y)*1./(C.x-D.x)*1.);
			float b2=C.y-a2*C.x;
			xIntersect=A.x;
			yIntersect=a2*xIntersect+b2;
		}
		else if(A.x!=B.x && C.x==D.x) {
			float a1=(float) ((A.y-B.y)*1./(A.x-B.x)*1.);
			float b1=A.y-a1*A.x;
			xIntersect=C.x;
			yIntersect=a1*xIntersect+b1;
		}
		else {
			return null;
		}
		return new Point(Math.round(xIntersect), Math.round(yIntersect));
			
	}
	
	
	public  Point projectionPointSeg(Point S1,Point S2,Point P ){	
		float sx1=S1.x,sy1=S1.y,sx2=S2.x,sy2=S2.y,px=P.x,py=P.y;
	    double xDelta = sx2 - sx1;
	    double yDelta = sy2 - sy1;

	    double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
	    if (u<0||u>1) {return null;};
	    Point closestPoint =new Point();
	    
	    closestPoint.x =  (int) (Math.round(sx1 + u * xDelta));
	    closestPoint.y =  (int) (Math.round(sy1 + u * yDelta));
	    
	    

	    return closestPoint;
	  }
	
	public  Point projectionPointDroite(Point S1,Point S2,Point P ){	
		float sx1=S1.x,sy1=S1.y,sx2=S2.x,sy2=S2.y,px=P.x,py=P.y;
	    double xDelta = sx2 - sx1;
	    double yDelta = sy2 - sy1;

	    double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
	    Point closestPoint =new Point();
	    
	    closestPoint.x =  (int) (sx1 + u * xDelta);
	    closestPoint.y =  (int) (sy1 + u * yDelta);
	    
	    

	    return closestPoint;
	  }
	
	
	
	
	
	public Vecteur calculVecteurCollisionRectDroitObstacleDroitAvecFrottement(FormRect rectangle0,FormRect rectangle, FormRect obstacle) {
		Point[] rect0 = Rect2Array(rectangle0);
		Point[] rect = Rect2Array(rectangle);
		Point[] obs = Rect2Array(obstacle);
		
		ArrayList<Integer> listPointInObs =new ArrayList<>();
		if (Collision(obs, 4, rect[0])) {listPointInObs.add(0);}
		if (Collision(obs, 4, rect[1])) {listPointInObs.add(1);}
		if (Collision(obs, 4, rect[2])) {listPointInObs.add(2);}
		if (Collision(obs, 4, rect[3])) {listPointInObs.add(3);}
		
		switch (listPointInObs.size()) {
		
		case 2 :
			int t= listPointInObs.get(0);
			Point A0 =rect0[t],A =rect[t];
			Point I =new Point();
			for (int j=0;j<4;j++) {
				Point S1=obs[j],S2=obs[(j+3)%4];
				Point J =calculIntersectionSeg(A0, A, S1, S2);
				if (J!=null) {I=J;}
			}
			Vecteur vec=new Vecteur();
			vec.x=I.x-A.x;vec.y=I.y-A.y;
			return vec; 
			
		case 1 :
			int t1= listPointInObs.get(0);
			Point B0 =rect0[t1],B =rect[t1];
			Point P =new Point();
			int n=0;
			for (int j=0;j<4;j++) {
				if (Collision(rect, 4 , obs[j])) { P=obs[j];n=j;}
			}
			
			Vecteur B0B =new Vecteur(),B0P=new Vecteur();
			B0B.x=B.x-B0.x;B0B.y=B.y-B0.y;
			B0P.x=P.x-B0.x;B0P.y=P.y-B0.y;
			Point P2;
			if (determinant(B0P,B0B)<0) {P2=obs[(n+3)%4];}
			else {P2=obs[(n+3)%4];}
			Point Inter =calculIntersectionSeg(B0, B, P, P2);
			Vecteur vec2=new Vecteur();
			vec2.x=Inter.x-B.x;vec2.y=Inter.y-B.y;
			return vec2;
			
		default :
			return null;
			}
		
	}
	
	public Vecteur calculVecteurCollisionRectDroitObstacleDroitSansFrottement(FormRect rectangle0,FormRect rectangle, FormRect obstacle) {
		Point[] rect0 = Rect2Array(rectangle0);
		Point[] rect = Rect2Array(rectangle);
		Point[] obs = Rect2Array(obstacle);

		ArrayList<Integer> listPointInObs =new ArrayList<>();
		if (Collision(obs, 4, rect[0])) {listPointInObs.add(0);}
		if (Collision(obs, 4, rect[1])) {listPointInObs.add(1);}
		if (Collision(obs, 4, rect[2])) {listPointInObs.add(2);}
		if (Collision(obs, 4, rect[3])) {listPointInObs.add(3);}
		
		switch (listPointInObs.size()) {
		
		case 2 :

			int t= listPointInObs.get(0);;
			Point A0 =rect0[t],A =rect[t];
			Point I =new Point();
			for (int j=0;j<4;j++) {
				Point S1=obs[j],S2=obs[(j+3)%4];
				Point J =calculIntersectionSeg(A0, A, S1, S2);
				if (J!=null) {I=ProjectionI(S1, S2, A);}
			}
			Vecteur vec=new Vecteur();
			vec.x=I.x-A.x;vec.y=I.y-A.y;
			return vec; 
			
		case 1 :

			int t1= listPointInObs.get(0);
			Point B0 =rect0[t1],B =rect[t1];
			Point P =new Point();
			int n=0;
			for (int j=0;j<4;j++) {
				if (Collision(rect, 4 , obs[j])) { P=obs[j];n=j;}
			}
			Vecteur B0B =new Vecteur(),B0P=new Vecteur();
			B0B.x=B.x-B0.x;B0B.y=B.y-B0.y;
			B0P.x=P.x-B0.x;B0P.y=P.y-B0.y;
			Point P2;
			if (determinant(B0P,B0B)<0) {P2=obs[(n+1)%4];}
			else {P2=obs[(n+3)%4];}
			Point I1 =projectionPointSeg(P, P2, B);
			Vecteur vec2=new Vecteur();
			vec2.x=I1.x-B.x;vec2.y=I1.y-B.y;
			return vec2;
			
		default :
			return null;
			}
		
	}
	
	
	
	
	
	
	
	public Vecteur calculVecteurCollisionCircleObstacleDroitSansFrottement(FormCircle circle, FormRect obstacle) {
		Cercle C =new Cercle();
		C.x=circle.getX();C.y=circle.getY();C.radius=circle.getRadius();
		
		Point[] obs = Rect2Array(obstacle);

		ArrayList<Integer> listPointObsCollision =new ArrayList<>();
		if (CollisionSegment(obs[0], obs[1], C)) {listPointObsCollision.add(0);}
		if (CollisionSegment(obs[1], obs[2], C)) {listPointObsCollision.add(1);}
		if (CollisionSegment(obs[2], obs[3], C)) {listPointObsCollision.add(2);}
		if (CollisionSegment(obs[3], obs[0], C)) {listPointObsCollision.add(3);}

		switch (listPointObsCollision.size()) {
		
		case 2 :
			Point P0=new Point(C.x,C.y);
			int t= listPointObsCollision.get(1);;
			Point S =obs[t];
			Point Sprime= obs[(t+2)%4];
			Vecteur CS =new Vecteur(),CSprime=new Vecteur();
			CS.x=S.x-C.x;CS.y=S.y-C.y;
			CSprime.x=Sprime.x-C.x;CSprime.y=Sprime.y-C.y;
			Point I=new Point();
			if (determinant(CS,CSprime)>0) { 
				 I=projectionPointDroite(S, obs[(t+3)%4],P0);
				}
			else {  
				I=projectionPointDroite(S, obs[(t+1)%4],P0);
			}

			
			Vecteur IC=new Vecteur();
			IC.x=C.x-I.x;IC.y=C.y-I.y;
			Vecteur IS=new Vecteur();
			IS.x=S.x-S.x;IS.y=C.y-S.y;
			normalize(IC);
			float normIS =norm(IS);
			float normIC = (float) Math.sqrt(C.radius*C.radius-normIS*normIS);
			Vecteur vec=new Vecteur();
			vec.x=IC.x*normIC;vec.y=IC.y*normIC;
			return vec;
			
		case 1 :

			int t1= listPointObsCollision.get(0);
			Point S1 =obs[t1],S2 =obs[(t1+1)%4];	
			Point C1=new Point(C.x,C.y);
			Point I1 =projectionPointSeg(S1, S2,C1);
			Vecteur I1C=new Vecteur();
			I1C.x=C.x-I1.x;
			I1C.y=C.y-I1.y;
			float normI1C =norm(I1C);
			Vecteur vec2=new Vecteur();
			vec2.x=I1C.x*(C.radius/normI1C-1);vec2.y=I1C.y*(C.radius/normI1C-1);
			
			return vec2;
			
		default :
			return null;
			}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
    ////////////////////////////////////
	////// FONCTIONS GENERALES /////////
	////////////////////////////////////
	
	

	
	public float determinant(Vecteur vec1, Vecteur vec2) {
		return vec1.x*vec2.y-vec1.y*vec2.x;
	}
	
	
	
	
	
	
	
	public boolean Collision(int curseur_x,int curseur_y,Box box){

	   if (curseur_x >= box.x 

	    && curseur_x < box.x + box.w

	    && curseur_y >= box.y 

	    && curseur_y < box.y + box.h)

	       return true;

	   else

	       return false;

	}
	
	public static boolean Collision(Box box1,Box box2){

	   if((box2.x >= box1.x + box1.w)      // trop à droite

	    || (box2.x + box2.w <= box1.x) // trop à gauche

	    || (box2.y >= box1.y + box1.h) // trop en bas

	    || (box2.y + box2.h <= box1.y))  // trop en haut

	          return false; 

	   else

	          return true; 

	}

	public boolean CollisionPointCercle(float x ,float y,Cercle C){
	   int d2 = (int) ((x-C.x)*(x-C.x) + (y-C.y)*(y-C.y));

	   if (d2>C.radius*C.radius)

	      return false;

	   else

	      return true;

	}

	public boolean Collision(Cercle C1,Cercle C2){
	   int d2 = (C1.x-C2.x)*(C1.x-C2.x) + (C1.y-C2.y)*(C1.y-C2.y);
	   if (d2 > (C1.radius + C2.radius)*(C1.radius + C2.radius))
	      return false;
	   else
	      return true;
	}

	
	
	
	public boolean Collision(Point tab[],int nbp,Point P){
	  int i;
	  for(i=0;i<nbp;i++)
	  {
	     Point A = tab[i];
	     Point B;
	     if (i==nbp-1)  // si c'est le dernier point, on relie au premier
	         B = tab[0];
	     else           // sinon on relie au suivant.
	         B = tab[i+1];
	     Vecteur D=new Vecteur(),T=new Vecteur();
	     D.x = B.x - A.x;
	     D.y = B.y - A.y;
	     T.x = P.x - A.x;
	     T.y = P.y - A.y;
	     float d = D.x*T.y - D.y*T.x;
	     if (d<	0)
	        return false;  // un point à droite et on arrête tout.
	  }
	  return true;  // si on sort du for, c'est qu'aucun point n'est à gauche, donc c'est bon.
	}
		
	public int intersectsegment(Point A,Point B,Point I,Point P){
	   Vecteur D=new Vecteur(),E=new Vecteur();

	   D.x = B.x - A.x;

	   D.y = B.y - A.y;

	   E.x = P.x - I.x;

	   E.y = P.y - I.y;

	   double denom = D.x*E.y - D.y*E.x;

	   if (denom==0)

	       return -1;   // erreur, cas limite

	   double t = - (A.x*E.y-I.x*E.y-E.x*A.y+E.x*I.y) / denom;

	   if (t<0 || t>=1)

	      return 0;

	   double u = - (-D.x*A.y+D.x*I.y+D.y*A.x-D.y*I.x) / denom;

	   if (u<0 || u>=1)

	      return 0;

	   return 1;

	}
	
	public boolean CollisionDroite(Point A,Point B,Cercle C){
	   Vecteur u=new Vecteur();
	   u.x = B.x - A.x;
	   u.y = B.y - A.y;
	   Vecteur AC =new Vecteur();
	   AC.x = C.x - A.x;
	   AC.y = C.y - A.y;
	   float numerateur = u.x*AC.y - u.y*AC.x;   // norme du vecteur v
	   if (numerateur <0)
	      numerateur = -numerateur ;   // valeur absolue ; si c'est négatif, on prend l'opposé.
	   float denominateur = (float) Math.sqrt(u.x*u.x + u.y*u.y);  // norme de u s
	   float CI = numerateur / denominateur;
	   if (CI<=C.radius)
	      return true;
	   else
	      return false;
	}

	
	public boolean CollisionSegment(Point A,Point B,Cercle C)
	{
			   if (CollisionDroite(A,B,C) == false) {
	     return false;  // si on ne touche pas la droite, on ne touchera jamais le segment
	   }
	   Vecteur AB=new Vecteur(),AC=new Vecteur(),BC=new Vecteur();
	   AB.x = B.x - A.x;
	   AB.y = B.y - A.y;
	   AC.x = C.x - A.x;
	   AC.y = C.y - A.y;
	   BC.x = C.x - B.x;
	   BC.y = C.y - B.y;
	   float pscal1 = AB.x*AC.x + AB.y*AC.y;  // produit scalaire
	   float pscal2 = (-AB.x)*BC.x + (-AB.y)*BC.y;  // produit scalaire
	   if (pscal1>=0 && pscal2>=0)
	      return true;   // I entre A et B, ok.
	   // dernière possibilité, A ou B dans le cercle
	   if (CollisionPointCercle(A.x,A.y,C))
	     return true;
	   if (CollisionPointCercle(B.x,B.y,C))
	     return true;
	   return false;
	}

	public Point ProjectionI(Point A,Point B,Point C){
	  Vecteur u=new Vecteur(),AC=new Vecteur();
	  u.x = B.x - A.x; 
	  u.y = B.y - A.y; 
	  AC.x = C.x - A.x;
	  AC.y = C.y - A.y;
	  float ti = (u.x*AC.x + u.y*AC.y)/(u.x*u.x + u.y*u.y);
	  Point I=new Point();
	  I.x = (int) (A.x + ti*u.x);
	  I.y = (int) (A.y + ti*u.y);
	  return I;
	}
	
	public Vecteur GetNormale(Point A,Point B,Point C){
	  Vecteur AC=new Vecteur(),u=new Vecteur(),N=new Vecteur();
	  u.x = B.x - A.x;  
	  u.y = B.y - A.y;
	  AC.x = C.x - A.x;  
	  AC.y = C.y - A.y;
	  float parenthesis = u.x*AC.y-u.y*AC.x;  // calcul une fois pour les deux
	  N.x = -u.y*(parenthesis);
	  N.y = u.x*(parenthesis);
	  // normalisons
	  float norme = (float) Math.sqrt(N.x*N.x + N.y*N.y);
	  N.x/=norme;
	  N.y/=norme;
	  return N;
	}
	
	public Vecteur CalculerVecteurV2(Vecteur v,Vecteur N){
	  Vecteur v2=new Vecteur();
	  float pscal = (v.x*N.x +  v.y*N.y);
	  v2.x = v.x -2*pscal*N.x;
	  v2.y = v.y -2*pscal*N.y;
	  return v2;
	}

	public boolean CollisionDroiteSeg(Point A,Point B,Point O,Point P)
	{
	  Vecteur AO=new Vecteur(),AP=new Vecteur(),AB=new Vecteur();
	  AB.x = B.x - A.x;
	  AB.y = B.y - A.y;
	  AP.x = P.x - A.x;
	  AP.y = P.y - A.y;
	  AO.x = O.x - A.x;
	  AO.y = O.y - A.y;
	  if ((AB.x*AP.y - AB.y*AP.x)*(AB.x*AO.y - AB.y*AO.x)<0)
	     return true;
	  else
	     return false;
	}

	public boolean CollisionSegSeg(Point A,Point B,Point O,Point P)
	{
	
	  if (CollisionDroiteSeg(A,B,O,P)==false)
	     return false;  // inutile d'aller plus loin si le segment [OP] ne touche pas la droite (AB)
	  Vecteur AB=new Vecteur(),OP=new Vecteur();
	  AB.x = B.x - A.x;
	  AB.y = B.y - A.y;
	  OP.x = P.x - O.x;
	  OP.y = P.y - O.y;
	  float k = -(A.x*OP.y-O.x*OP.y-OP.x*A.y+OP.x*O.y)/(AB.x*OP.y-AB.y*OP.x);
	  if (k<0 || k>1)
	     return false;
	  else
	     return true;
	}

	public boolean CollisionCercleAABB(Cercle C1,Box box1)
	{
	   Box boxCercle = new Box();  // retourner la bounding box de l'image porteuse, ou calculer la bounding box.
	   boxCercle.x=C1.x-C1.radius;
	   boxCercle.y=C1.y-C1.radius;
	   boxCercle.w=2*C1.radius;
	   boxCercle.h=2*C1.radius;
	   if (!(Collision(box1,boxCercle)))
	      return false;   // premier test
	   if (CollisionPointCercle(box1.x,box1.y,C1)
	    || CollisionPointCercle(box1.x,box1.y+box1.h,C1)
	    || CollisionPointCercle(box1.x+box1.w,box1.y,C1)
	    || CollisionPointCercle(box1.x+box1.w,box1.y+box1.h,C1))
	      return true;   // deuxieme test
	   if (Collision(C1.x,C1.y,box1))
	      return true;   // troisieme test
	   int projvertical = ProjectionSurSegment(C1.x,C1.y,box1.x,box1.y,box1.x,box1.y+box1.h);
	   int projhorizontal = ProjectionSurSegment(C1.x,C1.y,box1.x,box1.y,box1.x+box1.w,box1.y); 
	   if (projvertical==1 || projhorizontal==1)
	      return true;   // cas E
	   return false;  // cas B   
	}

	public int ProjectionSurSegment(int Cx,int Cy,int Ax,int Ay,int Bx,int By)
	{
	   int ACx = Cx-Ax;
	   int ACy = Cy-Ay; 
	   int ABx = Bx-Ax;
	   int ABy = By-Ay; 
	   int BCx = Cx-Bx;
	   int BCy = Cy-By; 
	   int s1 = (ACx*ABx) + (ACy*ABy);
	   int s2 = (BCx*ABx) + (BCy*ABy); 
	   if (s1*s2>0)
	     return 0;
	   return 1;
	}




	
}
