package com.example.conectamovil.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.conectamovil.Model.User;
import com.example.conectamovil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class activity_contacts extends AppCompatActivity {

    private EditText txtId, txtNombreContacto, txtcorreoContacto, txtedadContacto, txtnacionalidadContacto;
    private Button btnGuardar, btnEliminar, btnModificar, btnBuscarContactos;
    private DatabaseReference databaseReference;

    private List<User> contactList;
    private ContactsAdapter contactsAdapter;
    private ListView listViewContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        txtId = findViewById(R.id.txtid);
        txtNombreContacto = findViewById(R.id.txtnombreContacto);
        btnGuardar = findViewById(R.id.btnRegistrar);
        btnModificar = findViewById(R.id.btnModificar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnBuscarContactos = findViewById(R.id.btnBuscarContactos);
        txtcorreoContacto = findViewById(R.id.txtcorreoContacto);
        txtedadContacto = findViewById(R.id.txtedadContacto);
        txtnacionalidadContacto = findViewById(R.id.txtnacionalidadContacto);
        listViewContacts = findViewById(R.id.listViewContacts);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");

        contactList = new ArrayList<>();
        contactsAdapter = new ContactsAdapter(this, contactList);
        listViewContacts.setAdapter(contactsAdapter);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarDatos();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificarDatos();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarDatos();
            }
        });

        btnBuscarContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarContactos();
            }
        });
    }

    private void buscarContactos() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User contact = snapshot.getValue(User.class);
                    contactList.add(contact);
                }

                contactsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(activity_contacts.this, "Error al buscar contactos", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void guardarDatos() {
        String id = txtId.getText().toString().trim();
        String nombreContacto = txtNombreContacto.getText().toString().trim();
        String correoContacto = txtcorreoContacto.getText().toString().trim();
        String edadContacto = txtedadContacto.getText().toString().trim();
        String nacionalidadContacto = txtnacionalidadContacto.getText().toString().trim();

        if (!id.isEmpty() && !nombreContacto.isEmpty() && !correoContacto.isEmpty() && !edadContacto.isEmpty()&& !nacionalidadContacto.isEmpty()) {
            DatabaseReference contactoRef = databaseReference.child(id);

            contactoRef.child("nombreContacto").setValue(nombreContacto);
            contactoRef.child("correoContacto").setValue(correoContacto);
            contactoRef.child("edadContacto").setValue(edadContacto);
            contactoRef.child("nacionalidadContacto").setValue(nacionalidadContacto);


            Toast.makeText(this, "Datos guardados en Firebase", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }


    private void eliminarDatos() {
        String id = txtId.getText().toString().trim();

        if (!id.isEmpty()) {
            databaseReference.child(id).removeValue();
            Toast.makeText(this, "Datos eliminados en Firebase", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Completa el campo ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarDatos() {
        String id = txtId.getText().toString().trim();
        String nuevoNombre = txtNombreContacto.getText().toString().trim();

        if (!id.isEmpty() && !nuevoNombre.isEmpty()) {
            databaseReference.child(id).removeValue();

            databaseReference.child(id).child("Nombre").setValue(nuevoNombre);

            Toast.makeText(this, "Datos modificados en Firebase", Toast.LENGTH_SHORT).show();
            limpiarCampos();
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombreContacto.setText("");
        txtcorreoContacto.setText("");
        txtedadContacto.setText("");
        txtnacionalidadContacto.setText("");
    }
}
