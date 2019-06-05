using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CollectArticles : MonoBehaviour {

    public AudioClip son;
    public AudioSource source;

	// Use this for initialization
	void Start () {
        source.clip = son;
	}
	
	// Update is called once per frame
	void Update () {
		
	}

    void OnTriggerEnter(Collider col)
    {
        if (col.gameObject.tag=="Article")
        {
            Destroy(col.gameObject);
            source.Play();
        }
    }
}
