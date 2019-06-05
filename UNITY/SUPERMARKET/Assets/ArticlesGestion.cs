using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ArticlesGestion : MonoBehaviour {

    public int nbMaxArticle = 5;
    public GameObject article;

	// Use this for initialization
	void Start () {
        StartCoroutine(AddArticle());
        
    }
	
	// Update is called once per frame
	void Update () {
        
    }

    IEnumerator AddArticle()
    {
        while (true)
        {

            
            yield return new WaitForSeconds(2);
            
            // Si on doit ajouter un article
            if (transform.childCount < nbMaxArticle)
            {
                // Position random sur le tapis du rayon
                float xArticle = Random.Range(-1/2f,1/2f);
                float zArticle = Random.Range(-1/2f,1/2f);


                Vector3 scale = article.transform.lossyScale;
                GameObject newArticle=Instantiate(article, transform);
                newArticle = resizeObj(newArticle);

                newArticle.transform.localPosition = new Vector3(xArticle, newArticle.transform.localScale[1] / 2, zArticle);
            }
        }
        
    }

    GameObject resizeObj(GameObject obj)
    {
        Vector3 scale = new Vector3(1,1,1);
        Transform parentTrans = obj.transform.parent;
        while (parentTrans != null)
        {
            Vector3 parentScale = parentTrans.localScale;
            scale[0] = scale[0] / parentScale[0];
            scale[1] = scale[1] / parentScale[1];
            scale[2] = scale[2] / parentScale[2];

            parentTrans = parentTrans.parent;
        }
        obj.transform.localScale = scale;

        return obj;
    }




}
