package com.example.firebasedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.firebasedemo.databinding.ActivityMainBinding
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var firestore:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()
//        var myStudent = Students("Peter Parker","Grade 1",7)
//        var myStudent = Students("MJ Watson","Grade 1",8)
        var myStudent = Students("Michee Dee","Grade 3",6)
         addData(myStudent)
         getOneData("students","XyzXMoAscAFhigQBBmMI")
         getAllData("students")
         deleteData("students","8TDoKUn4U2dkbT53jK8o")
         updateData("students", "XyzXMoAscAFhigQBBmMI")
    }
    private fun addData(students: Students){
        Firebase.firestore.collection("students")
            .add(students).addOnSuccessListener {
            Log.d("SUCCESS_TAG","Success!")
        }
            .addOnFailureListener {e->
            Log.e("SUCCESS_TAG","Failed! $e")
            }
    }
    private fun getOneData(collectionName:String,documentId: String){
        val db = FirebaseFirestore.getInstance()
        val collectionRef=db.collection(collectionName)
        val documentRef=collectionRef.document(documentId)
        documentRef.get()
            .addOnSuccessListener {documentSnapshot->
                if(documentSnapshot.exists()){
                    val student = documentSnapshot.data
                    val message ="Student $student"
                    Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(applicationContext,"Document does not exist",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Error Occured!",Toast.LENGTH_SHORT).show()
            }
    }
    private fun getAllData(collectionName:String) {
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection(collectionName)
        collectionRef.get()
            .addOnSuccessListener {querySnapShot->
                for(documentSnapShot in querySnapShot){
                    val student = documentSnapShot.data
                    val message ="Student: $student"
                    Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Error Occured!",Toast.LENGTH_SHORT).show()
            }
    }
    private fun deleteData(collectionName:String,documentId: String) {
        val db = FirebaseFirestore.getInstance()
        val collectionRef=db.collection(collectionName)
        val documentRef=collectionRef.document(documentId)
        documentRef.delete()
            .addOnSuccessListener {
                Toast.makeText(applicationContext,"Document deleted successfully!",
                    Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Error Occured!",Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateData(collectionName:String,documentId: String) {
        val db = FirebaseFirestore.getInstance()
        val collectionRef=db.collection(collectionName)
        val documentRef=collectionRef.document(documentId)

        val newStudentData= hashMapOf<String,Any?>(
            "age" to 9,
            "gradeLevel" to "Grade 3",
            "score" to 10
        )
        documentRef.update(newStudentData)
            .addOnSuccessListener {
                Toast.makeText(applicationContext,"Document updated!",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext,"Error Occured!",Toast.LENGTH_SHORT).show()
            }
    }
}