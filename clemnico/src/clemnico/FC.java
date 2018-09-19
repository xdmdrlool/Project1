package clemnico;


public class FC {

	public FC() {
		// TODO Auto-generated constructor stub
	}
	
	
	
	public class  AABB{
	
	  int x;
	  int y;
	  int w;
	  int h;
	};
	
	public class Cercle{
	   int x,y;
	   int rayon;
	};

	public class Point{
	  float x,y;
	};

	public class Vecteur{
	  float x,y;
	};

	
	public boolean Collision(int curseur_x,int curseur_y,AABB box){

	   if (curseur_x >= box.x 

	    && curseur_x < box.x + box.w

	    && curseur_y >= box.y 

	    && curseur_y < box.y + box.h)

	       return true;

	   else

	       return false;

	}
	
	public boolean Collision(AABB box1,AABB box2){

	   if((box2.x >= box1.x + box1.w)      // trop � droite

	    || (box2.x + box2.w <= box1.x) // trop � gauche

	    || (box2.y >= box1.y + box1.h) // trop en bas

	    || (box2.y + box2.h <= box1.y))  // trop en haut

	          return false; 

	   else

	          return true; 

	}

	public boolean CollisionPointCercle(float x ,float y,Cercle C){
	   int d2 = (int) ((x-C.x)*(x-C.x) + (y-C.y)*(y-C.y));

	   if (d2>C.rayon*C.rayon)

	      return false;

	   else

	      return true;

	}

	public boolean Collision(Cercle C1,Cercle C2){
	   int d2 = (C1.x-C2.x)*(C1.x-C2.x) + (C1.y-C2.y)*(C1.y-C2.y);
	   if (d2 > (C1.rayon + C2.rayon)*(C1.rayon + C2.rayon))
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
	     Vecteur D,T;
	     D.x = B.x - A.x;
	     D.y = B.y - A.y;
	     T.x = P.x - A.x;
	     T.y = P.y - A.y;
	     float d = D.x*T.y - D.y*T.x;
	     if (d>	0)
	        return false;  // un point � droite et on arr�te tout.
	  }
	  return true;  // si on sort du for, c'est qu'aucun point n'est � gauche, donc c'est bon.
	}
		
	public int intersectsegment(Point A,Point B,Point I,Point P){
	   Vecteur D,E;

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
	   Vecteur u;
	   u.x = B.x - A.x;
	   u.y = B.y - A.y;
	   Vecteur AC;
	   AC.x = C.x - A.x;
	   AC.y = C.y - A.y;
	   float numerateur = u.x*AC.y - u.y*AC.x;   // norme du vecteur v
	   if (numerateur <0)
	      numerateur = -numerateur ;   // valeur absolue ; si c'est n�gatif, on prend l'oppos�.
	   float denominateur = (float) Math.sqrt(u.x*u.x + u.y*u.y);  // norme de u s
	   float CI = numerateur / denominateur;
	   if (CI<C.rayon)
	      return true;
	   else
	      return false;
	}

	
	public boolean CollisionSegment(Point A,Point B,Cercle C)
	{
	   if (CollisionDroite(A,B,C) == false)
	     return false;  // si on ne touche pas la droite, on ne touchera jamais le segment
	   Vecteur AB,AC,BC;
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
	   // derni�re possibilit�, A ou B dans le cercle
	   if (CollisionPointCercle(A.x,A.y,C))
	     return true;
	   if (CollisionPointCercle(B.x,B.y,C))
	     return true;
	   return false;
	}

	public Point ProjectionI(Point A,Point B,Point C){
	  Vecteur u,AC;
	  u.x = B.x - A.x; 
	  u.y = B.y - A.y; 
	  AC.x = C.x - A.x;
	  AC.y = C.y - A.y;
	  float ti = (u.x*AC.x + u.y*AC.y)/(u.x*u.x + u.y*u.y);
	  Point I;
	  I.x = A.x + ti*u.x;
	  I.y = A.y + ti*u.y;
	  return I;
	}
	
	public Vecteur GetNormale(Point A,Point B,Point C){
	  Vecteur AC,u,N;
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
	  Vecteur v2;
	  float pscal = (v.x*N.x +  v.y*N.y);
	  v2.x = v.x -2*pscal*N.x;
	  v2.y = v.y -2*pscal*N.y;
	  return v2;
	}

	public boolean CollisionDroiteSeg(Point A,Point B,Point O,Point P)
	{
	  Vecteur AO,AP,AB;
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
	  Vecteur AB,OP;
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

	public boolean CollisionCercleAABB(Cercle C1,AABB box1)
	{
	   AABB boxCercle = new AABB();  // retourner la bounding box de l'image porteuse, ou calculer la bounding box.
	   boxCercle.x=C1.x-C1.rayon;
	   boxCercle.y=C1.y-C1.rayon;
	   boxCercle.w=2*C1.rayon;
	   boxCercle.h=2*C1.rayon;
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