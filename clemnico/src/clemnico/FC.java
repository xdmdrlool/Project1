package clemnico;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

import clemnico.FC.Vecteur;

public class FC {

	public FC() {
		// TODO Auto-generated constructor stub
	}

	public class Box {

		int x;
		int y;
		int w;
		int h;
	};

	public class Cercle {
		int x, y;
		int radius;
	};

	public class Vecteur {
		float x, y;
	};

	// Calcule la distance euclidienne entre deux points
	public double distance(Point A, Point B) {
		return Math.sqrt((A.x - B.x) * (A.x - B.x) + (A.y - B.y) * (A.y - B.y));
	}

	public float norm(Vecteur A) {
		return (float) Math.sqrt(A.x * A.x + A.y * A.y);
	}

	public void normalize(Vecteur A) {
		float r = norm(A);
		if (r != 0) {
			A.x = A.x / r;
			A.y = A.y / r;
		}

	}

	public Box RectDroit2Box(FormRect rect) {
		Box box = new Box();
		box.x = rect.getX();
		box.y = rect.getY();
		box.w = rect.getWidth();
		box.h = rect.getHeight();
		return box;

	}

	public Point[] Rect2Array(FormRect rect) {
		int x = rect.getX();
		int y = rect.getY();
		int w = rect.getWidth();
		int h = rect.getHeight();
		if (rect.getAngle() == 0) {
			return (new Point[] { new Point(x, y), new Point(x + w, y), new Point(x + w, y + h), new Point(x, y + h) });
		}
		double angle = Math.toRadians(rect.getAngle());
		double x0 = x + w / 2;
		double y0 = y + h / 2;
		double r = Math.sqrt(h * h + w * w) / 2;
		double alpha1 = -Math.atan(h * 1.0 / w);
		double alpha2 = -alpha1;
		double alpha3 = Math.PI + alpha1;
		double alpha4 = Math.PI + alpha2;
		Point[] t = { new Point(), new Point(), new Point(), new Point() };

		t[0].setLocation(
				new Point((int) (x0 + r * Math.cos(angle + alpha4)), (int) (y0 + r * Math.sin(angle + alpha4))));
		t[1].setLocation(
				new Point((int) (x0 + r * Math.cos(angle + alpha1)), (int) (y0 + r * Math.sin(angle + alpha1))));
		t[2].setLocation(
				new Point((int) (x0 + r * Math.cos(angle + alpha2)), (int) (y0 + r * Math.sin(angle + alpha2))));
		t[3].setLocation(
				new Point((int) (x0 + r * Math.cos(angle + alpha3)), (int) (y0 + r * Math.sin(angle + alpha3))));

		return t;
	}

	public Cercle Cirle2Cercle(FormCircle circle) {
		Cercle cercle = new Cercle();
		cercle.x = circle.getX();
		cercle.y = circle.getY();
		cercle.radius = circle.getRadius();
		return cercle;
	}

	public boolean Collision(Form form1, Form form2) {
		if (form1.getType() == "RECT") {
			FormRect rect1 = new FormRect(Color.BLACK, form1.getX(), form1.getY(), form1.getWidth(), form1.getHeight(),
					form1.getAngle());
			if (form2.getType() == "RECT") {
				FormRect rect2 = new FormRect(Color.BLACK, form2.getX(), form2.getY(), form2.getWidth(),
						form2.getHeight(), form2.getAngle());
				return Collision(rect1, rect2);
			} else {
				FormCircle circle2 = new FormCircle(Color.BLACK, form2.getX(), form2.getY(), form2.getRadius());
				return Collision(rect1, circle2);
			}
		}

		else {
			FormCircle circle1 = new FormCircle(Color.BLACK, form1.getX(), form1.getY(), form1.getRadius());
			if (form2.getType() == "RECT") {
				FormRect rect2 = new FormRect(Color.BLACK, form2.getX(), form2.getY(), form2.getWidth(),
						form2.getHeight(), form2.getAngle());
				return Collision(circle1, rect2);
			} else {
				FormCircle circle2 = new FormCircle(Color.BLACK, form2.getX(), form2.getY(), form2.getRadius());
				return Collision(circle1, circle2);
			}

		}
	}

	public boolean Collision(FormRect rect1, FormRect rect2) {
		Point[] list1 = Rect2Array(rect1);
		Point[] list2 = Rect2Array(rect2);
		for (int i = 0; i <= 3; i++) {
			if (Collision(list1, 4, list2[i]) || Collision(list2, 4, list1[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean Collision(FormRect rect, FormCircle circle) {
		Point[] t = Rect2Array(rect);
		Cercle cercle = Cirle2Cercle(circle);
		Point point = new Point();
		point.x = cercle.x;
		point.y = cercle.y;
		return Collision(t, 4, point) || CollisionSegment(t[0], t[1], cercle) || CollisionSegment(t[1], t[2], cercle)
				|| CollisionSegment(t[2], t[3], cercle) || CollisionSegment(t[3], t[0], cercle);
	}

	public boolean Collision(FormCircle circle, FormRect rect) {
		return Collision(rect, circle);
	}

	public boolean Collision(FormCircle circle1, FormCircle circle2) {
		return Collision(Cirle2Cercle(circle1), Cirle2Cercle(circle2));
	}

	public boolean CollisionLineRect(Point A, Point B, FormRect rectangle) {
		Point[] rect= Rect2Array(rectangle);
		Point J=new Point();
		for (int i=0; i<4; i++) {
			J=(calculIntersectionSeg(A,B,rect[i], rect[(i+1)%4]));
			if (J!=null) {
				return true;
			}
		}
		return false;
	}
	
	public Point calculIntersectionSeg(Point A, Point B, Point C, Point D) {
		if (!CollisionSegSeg(A, B, C, D)) {
			return null;
		}
		float xIntersect = -1;
		float yIntersect = -1;

		if (A.x != B.x && C.x != D.x) {
			// Pentes des segments
			float a1 = (float) ((A.y - B.y) * 1. / (A.x - B.x) * 1.);
			float a2 = (float) ((C.y - D.y) * 1. / (C.x - D.x) * 1.);
			if (a1 == a2) {
				return null;
			}
			// Ordonnees à l'origine
			float b1 = A.y - a1 * A.x;
			float b2 = C.y - a2 * C.x;
			xIntersect = (b2 - b1) / (a1 - a2);
			yIntersect = a1 * xIntersect + b1;
		} else if (A.x == B.x && C.x != D.x) {
			float a2 = (float) ((C.y - D.y) * 1. / (C.x - D.x) * 1.);
			float b2 = C.y - a2 * C.x;
			xIntersect = A.x;
			yIntersect = a2 * xIntersect + b2;

		} else if (A.x != B.x && C.x == D.x) {
			float a1 = (float) ((A.y - B.y) * 1. / (A.x - B.x) * 1.);
			float b1 = A.y - a1 * A.x;
			xIntersect = C.x;
			yIntersect = a1 * xIntersect + b1;

		} else {
			return null;
		}
		return new Point(Math.round(xIntersect), Math.round(yIntersect));

	}

	public Point projectionPointSeg(Point S1, Point S2, Point P) {
		float sx1 = S1.x, sy1 = S1.y, sx2 = S2.x, sy2 = S2.y, px = P.x, py = P.y;
		double xDelta = sx2 - sx1;
		double yDelta = sy2 - sy1;

		double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
		if (u < 0 || u > 1) {
			return null;
		}
		;
		Point closestPoint = new Point();
		closestPoint.x = (int) (Math.round(sx1 + u * xDelta));
		closestPoint.y = (int) (Math.round(sy1 + u * yDelta));

		return closestPoint;
	}

	public Point projectionPointDroite(Point S1, Point S2, Point P) {
		float sx1 = S1.x, sy1 = S1.y, sx2 = S2.x, sy2 = S2.y, px = P.x, py = P.y;
		double xDelta = sx2 - sx1;
		double yDelta = sy2 - sy1;

		double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
		Point closestPoint = new Point();

		closestPoint.x = (int) (sx1 + u * xDelta);
		closestPoint.y = (int) (sy1 + u * yDelta);

		return closestPoint;
	}

	public Vecteur[] calculVecteurCollisionRectDroitObstacleDroit(FormRect rectangle0, FormRect rectangle,FormRect obstacle) {
		int a=compteSommetIn(rectangle, obstacle);
		int b=compteSommetIn(obstacle, rectangle);
		if (a+b==0) {return null;}
		if (a>=b) {
			return calculVecteur( rectangle0,  rectangle, obstacle);
		}
		else {
			int x=obstacle.getX()+rectangle.getX()-rectangle0.getX();
			int y= obstacle.getY()+rectangle.getY()-rectangle0.getY();
			FormRect obstacle0=new FormRect(null,x,y, obstacle.getWidth(), obstacle.getHeight(), obstacle.getAngle());
			
			Vecteur[] tab2=calculVecteur(obstacle0, obstacle,rectangle);
			tab2[0].x*=-1;
			tab2[0].y*=-1;
			tab2[1].x*=-1;
			tab2[1].y*=-1;
			return tab2;
		}
	}
	
	
	private Vecteur[] calculVecteur(FormRect rectangle0, FormRect rectangle,FormRect obstacle) {
		Point Middle = new Point(rectangle.getX() + rectangle.getWidth() / 2,rectangle.getY() + rectangle.getHeight() / 2);
		Point[] rect0 = Rect2Array(rectangle0);
		Point[] rect = Rect2Array(rectangle);
		Point[] obs = Rect2Array(obstacle);
		Vecteur vec = new Vecteur();
		Vecteur direction = new Vecteur();
		Vecteur[] tab = new Vecteur[2];
		tab[0] = vec;
		tab[1] = direction;
		Point P = null;

		Point I = null;
		Point J = null;
		int n = 0;
		ArrayList<Integer> listPointInObs = new ArrayList<>();
		if (Collision(obs, 4, rect[0])) {listPointInObs.add(0);}
		if (Collision(obs, 4, rect[1])) {listPointInObs.add(1);}
		if (Collision(obs, 4, rect[2])) {listPointInObs.add(2);}
		if (Collision(obs, 4, rect[3])) {listPointInObs.add(3);}
		switch (listPointInObs.size()) {
		case 2:
//			System.out.println("cas 2");
			int t = listPointInObs.get(0);
			int t0 = listPointInObs.get(1);
			Point A0 = rect0[t], A = rect[t];
			Point A01 = rect0[t0], A1 = rect[t0];
			Point A02 = new Point((A0.x + A01.x) / 2, (A0.y + A01.y) / 2);
			Point A2 = new Point((A.x + A1.x) / 2, (A.y + A1.y) / 2);
//			System.out.println("A02 "+ A02.x+" "+A02.y);
//			System.out.println("A2  "+A2.x+" "+A2.y);

			for (int j = 0; j < 4; j++) {
				Point S1 = obs[j], S2 = obs[(j + 1) % 4];
				J = calculIntersectionSeg(A02, A2, S1, S2);
				if (J != null) {
					I = projectionPointSeg(S1, S2, A2);
				}
			}
			if (I == null) {
				vec.x = 0;
				vec.y = 0;
			} else {
				vec.x = I.x - A2.x;
				vec.y = I.y - A2.y;
			}
//			System.out.println(I.x+" "+I.y);
//			System.out.println(A2.x+" "+A2.y);
			if (vec.x != 0 || vec.y != 0) {
				direction.x = -vec.x;
				direction.y = -vec.y;
			} else {
				direction.x = (rect[t].x + rect[listPointInObs.get(1)].x) / 2 - Middle.x;
				direction.y = (rect[t].y + rect[listPointInObs.get(1)].y) / 2 - Middle.y;
			}
			break;
		case 1:
			int t1 = listPointInObs.get(0);
			Point B0 = rect0[t1], B = rect[t1];
			for (int j = 0; j < 4; j++) {
				if (Collision(rect, 4, obs[j])) {
					P = obs[j];
					n = j;
				}
			}
			Vecteur BB0 = new Vecteur(), BP = new Vecteur();
			BB0.x = B0.x - B.x;
			BB0.y = B0.y - B.y;
			BP.x = P.x - B.x;
			BP.y = P.y - B.y;
			Point S = obs[(n + 2) % 4];
			Vecteur SB = new Vecteur(), SP = new Vecteur();
			SB.x = B.x - S.x;
			SB.y = B.y - S.y;
			SP.x = S.x - P.x;
			SP.y = S.y - P.y;
			Point P2 = null;
			float det = determinant(BP, BB0);
			float det2 = determinant(SB, SP);
			if (B0.x == B.x && B0.y == B.y) {
				if (det2 >= 0) {
					P2 = obs[(n + 1) % 4];
					direction.x = -(P2.y - P.y);
					direction.y = (P2.x - P.x);
				} else {
					P2 = obs[(n + 3) % 4];
					direction.x = (P2.y - P.y);
					direction.y = -(P2.x - P.x);
				}
			} else {
				if (det > 0 || (det == 0 && det2 > 0)) {
					P2 = obs[(n + 1) % 4];
					direction.x = -(P2.y - P.y);
					direction.y = (P2.x - P.x);
				} else {
					P2 = obs[(n + 3) % 4];
					direction.x = (P2.y - P.y);
					direction.y = -(P2.x - P.x);
				}
			}
			Point I1 = projectionPointSeg(P, P2, B);
			if ((B0.x == B.x && B0.y == B.y) || det == 0) {
				vec.x = 0;
				vec.y = 0;
			} else {
				vec.x = I1.x - B.x;
				vec.y = I1.y - B.y;
			}
			break;
			
			
			
		default:
			return null;
		}
		normalize(direction);
		return tab;
	}
	
	public int compteSommetIn(FormRect rectangle,FormRect obstacle) {

		Point[] rect = Rect2Array(rectangle);
		Point[] obs = Rect2Array(obstacle);
		int n = 0;
		if (Collision(obs, 4, rect[0])) {n++;}
		if (Collision(obs, 4, rect[1])) {n++;}
		if (Collision(obs, 4, rect[2])) {n++;}
		if (Collision(obs, 4, rect[3])) {n++;}
		return n;
	}
	
	
	
	
	
	
	
	
	
	
	
	public Vecteur calculVecteurCollisionCircleObstacleDroit(FormCircle circle, FormRect obstacle) {
		Cercle C = new Cercle();
		C.x = circle.getX();
		C.y = circle.getY();
		C.radius = circle.getRadius();

		Point[] obs = Rect2Array(obstacle);

		ArrayList<Integer> listPointObsCollision = new ArrayList<>();
		if (CollisionSegment(obs[0], obs[1], C)) {
			listPointObsCollision.add(0);
		}
		if (CollisionSegment(obs[1], obs[2], C)) {
			listPointObsCollision.add(1);
		}
		if (CollisionSegment(obs[2], obs[3], C)) {
			listPointObsCollision.add(2);
		}
		if (CollisionSegment(obs[3], obs[0], C)) {
			listPointObsCollision.add(3);
		}

		switch (listPointObsCollision.size()) {

		case 2:
			Point P0 = new Point(C.x, C.y);
			int t = listPointObsCollision.get(1);
			;
			Point S = obs[t];
			Point Sprime = obs[(t + 2) % 4];
			Vecteur CS = new Vecteur(), CSprime = new Vecteur();
			CS.x = S.x - C.x;
			CS.y = S.y - C.y;
			CSprime.x = Sprime.x - C.x;
			CSprime.y = Sprime.y - C.y;
			Point I = new Point();
			Point S2 = new Point();
			if (determinant(CS, CSprime) > 0) {
				I = projectionPointDroite(S, obs[(t + 3) % 4], P0);
				S2 = obs[(t + 3) % 4];
			} else {
				I = projectionPointDroite(S, obs[(t + 1) % 4], P0);
				S2 = obs[(t + 1) % 4];
			}

			Vecteur IC = new Vecteur();
			IC.x = C.x - I.x;
			IC.y = C.y - I.y;
			Vecteur IS = new Vecteur();
			IS.x = S.x - S.x;
			IS.y = C.y - S.y;
			normalize(IC);
			float normIS = norm(IS);
			Vecteur vec = new Vecteur();
			float normIC = norm(IC);
			normalize(IC);

			// Test I dans [S1S2]
			Cercle crcle = new Cercle();
			crcle.x = I.x;
			crcle.y = I.y;
			crcle.radius = 1;
			if (CollisionSegment(S, S2, crcle)) {
				normIC = (float) C.radius - normIC;
				vec.x = IC.x * normIC;
				vec.y = IC.y * normIC;
				return vec;
			}

			else {
				normIC = (float) Math.sqrt(C.radius * C.radius - normIS * normIS) - normIC;
				vec.x = IC.x * normIC;
				vec.y = IC.y * normIC;
				return vec;
			}

		case 1:

			int t1 = listPointObsCollision.get(0);
			Point S1 = obs[t1], S3 = obs[(t1 + 1) % 4];
			Point C1 = new Point(C.x, C.y);
			Point I1 = projectionPointSeg(S1, S3, C1);
			Vecteur I1C = new Vecteur();
			I1C.x = C.x - I1.x;
			I1C.y = C.y - I1.y;
			float normI1C = norm(I1C);
			Vecteur vec2 = new Vecteur();
			vec2.x = I1C.x * (C.radius / normI1C - 1);
			vec2.y = I1C.y * (C.radius / normI1C - 1);

			return vec2;

		default:
			return null;
		}

	}

	////////////////////////////////////
	////// FONCTIONS GENERALES /////////
	////////////////////////////////////

	public float determinant(Vecteur vec1, Vecteur vec2) {
		return vec1.x * vec2.y - vec1.y * vec2.x;
	}

	public boolean Collision(int curseur_x, int curseur_y, Box box) {

		if (curseur_x >= box.x

				&& curseur_x < box.x + box.w

				&& curseur_y >= box.y

				&& curseur_y < box.y + box.h)

			return true;

		else

			return false;

	}

	public static boolean Collision(Box box1, Box box2) {

		if ((box2.x >= box1.x + box1.w) // trop à droite

				|| (box2.x + box2.w <= box1.x) // trop à gauche

				|| (box2.y >= box1.y + box1.h) // trop en bas

				|| (box2.y + box2.h <= box1.y)) // trop en haut

			return false;

		else

			return true;

	}

	public boolean CollisionPointCercle(float x, float y, Cercle C) {
		int d2 = (int) ((x - C.x) * (x - C.x) + (y - C.y) * (y - C.y));

		if (d2 > C.radius * C.radius)

			return false;

		else

			return true;

	}

	public boolean Collision(Cercle C1, Cercle C2) {
		int d2 = (C1.x - C2.x) * (C1.x - C2.x) + (C1.y - C2.y) * (C1.y - C2.y);
		if (d2 > (C1.radius + C2.radius) * (C1.radius + C2.radius))
			return false;
		else
			return true;
	}

	public boolean Collision(Point tab[], int nbp, Point P) {
		int i;
		for (i = 0; i < nbp; i++) {
			Point A = tab[i];
			Point B;
			if (i == nbp - 1) // si c'est le dernier point, on relie au premier
				B = tab[0];
			else // sinon on relie au suivant.
				B = tab[i + 1];
			Vecteur D = new Vecteur(), T = new Vecteur();
			D.x = B.x - A.x;
			D.y = B.y - A.y;
			T.x = P.x - A.x;
			T.y = P.y - A.y;
			float d = D.x * T.y - D.y * T.x;
			if (d < 0)
				return false; // un point à droite et on arrête tout.
		}
		return true; // si on sort du for, c'est qu'aucun point n'est à gauche, donc c'est bon.
	}

	public int intersectsegment(Point A, Point B, Point I, Point P) {
		Vecteur D = new Vecteur(), E = new Vecteur();

		D.x = B.x - A.x;

		D.y = B.y - A.y;

		E.x = P.x - I.x;

		E.y = P.y - I.y;

		double denom = D.x * E.y - D.y * E.x;

		if (denom == 0)

			return -1; // erreur, cas limite

		double t = -(A.x * E.y - I.x * E.y - E.x * A.y + E.x * I.y) / denom;

		if (t < 0 || t >= 1)

			return 0;

		double u = -(-D.x * A.y + D.x * I.y + D.y * A.x - D.y * I.x) / denom;

		if (u < 0 || u > 1)

			return 0;

		return 1;

	}

	public boolean CollisionDroite(Point A, Point B, Cercle C) {
		Vecteur u = new Vecteur();
		u.x = B.x - A.x;
		u.y = B.y - A.y;
		Vecteur AC = new Vecteur();
		AC.x = C.x - A.x;
		AC.y = C.y - A.y;
		float numerateur = u.x * AC.y - u.y * AC.x; // norme du vecteur v
		if (numerateur < 0)
			numerateur = -numerateur; // valeur absolue ; si c'est négatif, on prend l'opposé.
		float denominateur = (float) Math.sqrt(u.x * u.x + u.y * u.y); // norme de u s
		float CI = numerateur / denominateur;
		if (CI <= C.radius)
			return true;
		else
			return false;
	}

	public boolean CollisionSegment(Point A, Point B, Cercle C) {
		if (CollisionDroite(A, B, C) == false) {
			return false; // si on ne touche pas la droite, on ne touchera jamais le segment
		}
		Vecteur AB = new Vecteur(), AC = new Vecteur(), BC = new Vecteur();
		AB.x = B.x - A.x;
		AB.y = B.y - A.y;
		AC.x = C.x - A.x;
		AC.y = C.y - A.y;
		BC.x = C.x - B.x;
		BC.y = C.y - B.y;
		float pscal1 = AB.x * AC.x + AB.y * AC.y; // produit scalaire
		float pscal2 = (-AB.x) * BC.x + (-AB.y) * BC.y; // produit scalaire
		if (pscal1 >= 0 && pscal2 >= 0)
			return true; // I entre A et B, ok.
		// dernière possibilité, A ou B dans le cercle
		if (CollisionPointCercle(A.x, A.y, C))
			return true;
		if (CollisionPointCercle(B.x, B.y, C))
			return true;
		return false;
	}

	public Point ProjectionI(Point A, Point B, Point C) {
		Vecteur u = new Vecteur(), AC = new Vecteur();
		u.x = B.x - A.x;
		u.y = B.y - A.y;
		AC.x = C.x - A.x;
		AC.y = C.y - A.y;
		float ti = (u.x * AC.x + u.y * AC.y) / (u.x * u.x + u.y * u.y);
		Point I = new Point();
		I.x = (int) (A.x + ti * u.x);
		I.y = (int) (A.y + ti * u.y);
		return I;
	}

	public Vecteur GetNormale(Point A, Point B, Point C) {
		Vecteur AC = new Vecteur(), u = new Vecteur(), N = new Vecteur();
		u.x = B.x - A.x;
		u.y = B.y - A.y;
		AC.x = C.x - A.x;
		AC.y = C.y - A.y;
		float parenthesis = u.x * AC.y - u.y * AC.x; // calcul une fois pour les deux
		N.x = -u.y * (parenthesis);
		N.y = u.x * (parenthesis);
		// normalisons
		float norme = (float) Math.sqrt(N.x * N.x + N.y * N.y);
		N.x /= norme;
		N.y /= norme;
		return N;
	}

	public Vecteur CalculerVecteurV2(Vecteur v, Vecteur N) {
		Vecteur v2 = new Vecteur();
		float pscal = (v.x * N.x + v.y * N.y);
		v2.x = v.x - 2 * pscal * N.x;
		v2.y = v.y - 2 * pscal * N.y;
		return v2;
	}

	public boolean CollisionDroiteSeg(Point A, Point B, Point O, Point P) {
		Vecteur AO = new Vecteur(), AP = new Vecteur(), AB = new Vecteur();
		AB.x = B.x - A.x;
		AB.y = B.y - A.y;
		AP.x = P.x - A.x;
		AP.y = P.y - A.y;
		AO.x = O.x - A.x;
		AO.y = O.y - A.y;
		if ((AB.x * AP.y - AB.y * AP.x) * (AB.x * AO.y - AB.y * AO.x) <= 0)
			return true;
		else
			return false;
	}

	public boolean CollisionSegSeg(Point A, Point B, Point O, Point P) {

		if (CollisionDroiteSeg(A, B, O, P) == false) {
			return false; // inutile d'aller plus loin si le segment [OP] ne touche pas la droite (AB)
		}
		Vecteur AB = new Vecteur(), OP = new Vecteur();
		AB.x = B.x - A.x;
		AB.y = B.y - A.y;
		OP.x = P.x - O.x;
		OP.y = P.y - O.y;
		float k = -(A.x * OP.y - O.x * OP.y - OP.x * A.y + OP.x * O.y) / (AB.x * OP.y - AB.y * OP.x);
		if (k < 0 || k > 1) {
			;
			return false;
		} else
			return true;
	}

	public boolean CollisionCercleAABB(Cercle C1, Box box1) {
		Box boxCercle = new Box(); // retourner la bounding box de l'image porteuse, ou calculer la bounding box.
		boxCercle.x = C1.x - C1.radius;
		boxCercle.y = C1.y - C1.radius;
		boxCercle.w = 2 * C1.radius;
		boxCercle.h = 2 * C1.radius;
		if (!(Collision(box1, boxCercle)))
			return false; // premier test
		if (CollisionPointCercle(box1.x, box1.y, C1) || CollisionPointCercle(box1.x, box1.y + box1.h, C1)
				|| CollisionPointCercle(box1.x + box1.w, box1.y, C1)
				|| CollisionPointCercle(box1.x + box1.w, box1.y + box1.h, C1))
			return true; // deuxieme test
		if (Collision(C1.x, C1.y, box1))
			return true; // troisieme test
		int projvertical = ProjectionSurSegment(C1.x, C1.y, box1.x, box1.y, box1.x, box1.y + box1.h);
		int projhorizontal = ProjectionSurSegment(C1.x, C1.y, box1.x, box1.y, box1.x + box1.w, box1.y);
		if (projvertical == 1 || projhorizontal == 1)
			return true; // cas E
		return false; // cas B
	}

	public int ProjectionSurSegment(int Cx, int Cy, int Ax, int Ay, int Bx, int By) {
		int ACx = Cx - Ax;
		int ACy = Cy - Ay;
		int ABx = Bx - Ax;
		int ABy = By - Ay;
		int BCx = Cx - Bx;
		int BCy = Cy - By;
		int s1 = (ACx * ABx) + (ACy * ABy);
		int s2 = (BCx * ABx) + (BCy * ABy);
		if (s1 * s2 > 0)
			return 0;
		return 1;
	}
	
	
	
	
	////////////////////////////////////
	////// INTERACTIONS OBSTACLES //////
	////////////////////////////////////
	
	////PLAYER////
	public boolean obstacleInteraction(Entity entity, ArrayList<Obstacle> obstacles) {
		
		boolean collision=false;
		
		Vecteur vecteurCorrection=null;
		Vecteur directionCollision=null;
		boolean varInTheAir=true;
		boolean varCollisonLeft=false;
		boolean varCollisonRight=false;
//		System.out.println("");
		for (Obstacle obstacle: obstacles) {
			//S'il y a collision avec un obstacle
			FormRect rect0=new FormRect(Color.RED, entity.xBefore, entity.yBefore, entity.width, entity.height, 0);
			FormRect rect=(FormRect) entity.getHitbox().getForm();
			FormRect obs=(FormRect) obstacle.getHitbox().getForm();
			
			rect0.setX(rect0.getX()+obstacle.getX()-obstacle.getxBefore());
			rect0.setY(rect0.getY()+obstacle.getY()-obstacle.getyBefore());
			
			Vecteur[] tab = calculVecteurCollisionRectDroitObstacleDroit(rect0,rect,obs);
			
			if (tab !=null) {
				vecteurCorrection=tab[0];
				directionCollision=tab[1];
				
//				System.out.println("xB :"+rect0.getX()+"   yB : "+rect0.getY());
//				System.out.println("x :"+x+"   y : "+y);
//				System.out.println(vecteurCorrection.x+" "+vecteurCorrection.y);
//				System.out.println(directionCollision.x+" "+directionCollision.y);
				if (vecteurCorrection.y<0||directionCollision.y>0) {varInTheAir=false;}
				if (directionCollision.x<0) {varCollisonLeft=true;}
				if (directionCollision.x>0) {varCollisonRight=true;}
				if (directionCollision.x!=0 ||directionCollision.y>0) {entity.setVx(0);}
				if (directionCollision.y!=0) {entity.setVy(0);entity.setX(entity.getX()+obstacle.getVx());}
				

				int newX=(int) (entity.getX()+vecteurCorrection.x);
				int newY=(int) (entity.getY()+vecteurCorrection.y);
				
//				System.out.println("x: "+newX+"  y: "+newY);
//				System.out.println("air : "+isInTheAir());	

				entity.setX(newX);
				entity.setY(newY);
//				System.out.println("x :"+x+"   y : "+y+"     vy : "+vy+"     "+varInTheAir);
				collision=true;
			}
			

		}
//		System.out.println("x :"+x+"   y : "+y+"     vy : "+vy+"     "+varInTheAir);	
		entity.setTimeInAir(entity.getTimeInAir()+1);
		entity.setInTheAir(varInTheAir);
		entity.setInCollisionLeft(varCollisonLeft);
		entity.setInCollisionRight(varCollisonRight);
		
		entity.setxBefore(entity.x);entity.setyBefore(entity.y);

		return collision;
			
	}
	
	
	public ArrayList<Obstacle> concatenate(ArrayList<Obstacle> obstaclesFix, ArrayList<Obstacle> obstaclesMoving){
		ArrayList<Obstacle> obstacles = new ArrayList<>();
		for (Obstacle obstacle : obstaclesFix) {
			obstacles.add(obstacle);
		}
		for (Obstacle obstacle : obstaclesMoving) {
			obstacles.add(obstacle);
		}
		return obstacles;
	}
	
	////ENNEMIS////
	public void obstacleInteractionEnemy(Enemy enemy, ArrayList<Obstacle> obstacles) {
		
		
		Vecteur vecteurCorrection=null;
		Vecteur directionCollision=null;
		boolean varInTheAir=true;
		boolean inverseVx=false;
		for (Obstacle obstacle: obstacles) {
			//S'il y a collision avec un obstacle
			FormRect rect0=new FormRect(Color.RED, enemy.xBefore, enemy.yBefore, enemy.width, enemy.height, 0);
			FormRect rect=(FormRect) enemy.getHitbox().getForm();
			FormRect obs=(FormRect) obstacle.getHitbox().getForm();
			Vecteur[] tab = calculVecteurCollisionRectDroitObstacleDroit(rect0,rect,obs);
			
			if (tab !=null) {
				vecteurCorrection=tab[0];
				directionCollision=tab[1];
//				System.out.println(directionCollision.x+" "+directionCollision.y);
//				System.out.println("xB :"+xBefore+"   yB : "+yBefore);
//				System.out.println(vecteurCorrection.x+" "+vecteurCorrection.y);
				if (vecteurCorrection.y<0||directionCollision.y>0) {varInTheAir=false;}
				if (directionCollision.x!=0) {inverseVx=true;}
				if (!enemy.fallFromPlatform && directionCollision.y>0) {if(enemy.x<obstacle.getX() && enemy.vx<0) {inverseVx=true;} if (enemy.x+enemy.width>obstacle.getX()+obstacle.getWidth() &&enemy.vx>=0) {inverseVx=true;};}
				if (directionCollision.y!=0) {enemy.setVy(0);}

				int newX=(int) (enemy.getX()+vecteurCorrection.x);
				int newY=(int) (enemy.getY()+vecteurCorrection.y);
				
				
//				System.out.println("x: "+newX+"  y: "+newY);
//				System.out.println("air : "+isInTheAir());	

				enemy.setX(newX);
				enemy.setY(newY);
				
//				System.out.println("x :"+x+"   y : "+y+"     vy : "+vy+"     "+varInTheAir);
			}
			

		}
		enemy.setTimeInAir(enemy.getTimeInAir()+1);
		enemy.setInTheAir(varInTheAir);
		enemy.setxBefore(enemy.x);enemy.setyBefore(enemy.y);		
		if (inverseVx) {enemy.setVx(-enemy.vx);}
	}
	
	////////////////////////////////////
	////// INTERACTIONS PORTAILS ///////
	////////////////////////////////////


	public void portalInteractionRect(Entity entity, Portal portal1, Portal portal2) {
				
		Hitbox hitbox=entity.getHitbox();
		int x = entity.getX();
		int y = entity.getY();
		int vx = entity.getVx();
		int vy = entity.getVy();
		int width = entity.getWidth();
		int height = entity.getHeight();
		
		// S'il y a interaction avec l'un des deux portails
		if (hitbox.collision(portal1.getHitbox()) || hitbox.collision(portal2.getHitbox())) {
			
			// Détermine le portail d'entrée et de sortie
			Portal portalIn, portalOut;
			if (hitbox.collision(portal1.getHitbox())) {
				portalIn = portal1;
				portalOut = portal2;
			} else {
				portalIn = portal2;
				portalOut = portal1;
			}
	
			// Obtenir les points A et B des deux portails (axe, point, portail)
			// portalIn = 1 et portalOut = 2
			int xA1 = Rect2Array(portalIn.getForm())[0].x;
			int yA1 = Rect2Array(portalIn.getForm())[0].y;
			int xB1 = Rect2Array(portalIn.getForm())[1].x;
			int yB1 = Rect2Array(portalIn.getForm())[1].y;
			int xD1 = Rect2Array(portalIn.getForm())[3].x;
			int yD1 = Rect2Array(portalIn.getForm())[3].y;
			int xA2 = Rect2Array(portalOut.getForm())[0].x;
			int yA2 = Rect2Array(portalOut.getForm())[0].y;
			int xB2 = Rect2Array(portalOut.getForm())[1].x;
			int yB2 = Rect2Array(portalOut.getForm())[1].y;
			int xD2 = Rect2Array(portalOut.getForm())[3].x;
			int yD2 = Rect2Array(portalOut.getForm())[3].y;
	
			//Coordonnées du joueur modifiées
			int xj=x+width/2;
			int yj=y+height/2;
			
			//Détermine la vitesse min et la distance entre le joueur et le portail de sortie
			int distancePortal=(int) ( width/1.42);
			int vMinOut=5;
			
			
			//Détermine la position du joueur sur la tangente du portail
			double distancePlayerA1=Math.sqrt((xj-xA1)*(xj-xA1)+(yj-yA1)*(yj-yA1));
			double distancePlayerD1=Math.sqrt((xj-xD1)*(xj-xD1)+(yj-yD1)*(yj-yD1));
			double distancePlayerA2=portalOut.getWidth()-distancePlayerA1;
			
			//Détermine la norme de la vitesse en sortie
			double vNormIn=Math.sqrt(vx*vx+vy*vy); 						//Norme de la vitesse d'entrée
			double[] vectorVIn= {vx*1./vNormIn*1.,vy*1./vNormIn*1.};	//Vecteur vitesse normalisé
			double vNormOut=Math.max(vNormIn, vMinOut);					//Vitesse minorée en sortie de portail
			
			//Vecteurs normalisés AB et DA du portail In et Out
			double[] vectorAB1= {(xB1-xA1)*1./portalIn.getWidth()*1.,(yB1-yA1)*1./portalIn.getWidth()*1.};
			double[] vectorAB2= {(xB2-xA2)*1./portalOut.getWidth()*1.,(yB2-yA2)*1./portalOut.getWidth()*1.};
			double[] vectorDA2= {(xA2-xD2)*1./portalOut.getHeight()*1.,(yA2-yD2)*1./portalOut.getHeight()*1.};
			
			//Détermine l'orientation de la vitesse en sortie en fonction de celle en entrée
			double thetaIn=Math.acos(vectorAB1[0]*vectorVIn[0]+vectorAB1[1]*vectorVIn[1]);
			double thetaOut=(thetaIn-Math.PI);
			double[] vectorVOut= {(vectorAB2[0]*Math.cos(thetaOut)-vectorAB2[1]*Math.sin(thetaOut)),
							      (vectorAB2[0]*Math.sin(thetaOut)+vectorAB2[1]*Math.cos(thetaOut))};
			int[] vOut= {(int) (vectorVOut[0]*vNormOut), (int) (vectorVOut[1]*vNormOut)};
			
			//Détermine le côté du portail où passe le joueur
			correctionInteractionRect(entity, portalIn.getForm());
			if (distancePlayerA1<distancePlayerD1) {
				entity.setX((int) (xA2+vectorAB2[0]*distancePlayerA2+(distancePortal)*vectorDA2[0])-width/2);
				entity.setY((int) (yA2+vectorAB2[1]*distancePlayerA2+(distancePortal)*vectorDA2[1])-height/2);
				entity.setVx(vOut[0]);
				entity.setVy(vOut[1]);
			}
			else {
				
				entity.setX((int) (xA2+vectorAB2[0]*distancePlayerA2-(distancePortal+portalOut.getHeight())*vectorDA2[0])-width/2);
				entity.setY((int) (yA2+vectorAB2[1]*distancePlayerA2-(distancePortal+portalOut.getHeight())*vectorDA2[1])-height/2);
				entity.setVx(-vOut[0]);
				entity.setVy(-vOut[1]);
			}
		}
	}
	
	public void correctionInteractionRect(Entity entity, FormRect rect) {

		int xBefore = entity.getxBefore();
		int yBefore = entity.getyBefore();
		int x = entity.getX();
		int y = entity.getY();
		int width = entity.getWidth();
		int height = entity.getHeight();
		
		Point pointPlayer1=new Point(xBefore,yBefore);
		Point pointPlayer2=new Point(x,y);
		
		Point A=new Point(Rect2Array(rect)[0].x,Rect2Array(rect)[0].y);
		Point B=new Point(Rect2Array(rect)[1].x,Rect2Array(rect)[1].y);
		Point C=new Point(Rect2Array(rect)[2].x,Rect2Array(rect)[2].y);
		Point D=new Point(Rect2Array(rect)[3].x,Rect2Array(rect)[3].y);
		
		double dmin=10000;
		Point correctedPosition=new Point(-1,-1);
		
		if (calculIntersectionSeg(A,B,pointPlayer1,pointPlayer2)!=null && distance(A, B)<dmin)
			{correctedPosition=calculIntersectionSeg(A,B,pointPlayer1,pointPlayer2);
			dmin=distance(A, B);}
		
		
		if (calculIntersectionSeg(B,C,pointPlayer1,pointPlayer2)!=null && distance(B, C)<dmin)
			{correctedPosition=calculIntersectionSeg(B,C,pointPlayer1,pointPlayer2);
			dmin=distance(B, C);}
		
		
		if (calculIntersectionSeg(C,D,pointPlayer1,pointPlayer2)!=null && distance(C, D)<dmin)
			{correctedPosition=calculIntersectionSeg(C,D,pointPlayer1,pointPlayer2);
			dmin=distance(C, D);}
		
		
		if (calculIntersectionSeg(D,A,pointPlayer1,pointPlayer2)!=null && distance(D, A)<dmin)
			{correctedPosition=calculIntersectionSeg(D,A,pointPlayer1,pointPlayer2);
			dmin=distance(D, A);}

		if (correctedPosition.getX()!=-1 && correctedPosition.getY()!=-1) {
			entity.setX((int)(correctedPosition.x+width*1./2.));
			entity.setY((int)(correctedPosition.y+height*1./2.));
			
		}
	}
}
