package com.example.conectamovil.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.conectamovil.Model.User;
import com.example.conectamovil.R;

import java.util.List;

public class ContactsAdapter extends BaseAdapter {

    private Context context;
    private List<User> contactList;

    public ContactsAdapter(Context context, List<User> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_contact, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.txtNombre = convertView.findViewById(R.id.txtnombreContacto);
            viewHolder.txtCorreo = convertView.findViewById(R.id.txtcorreoContacto);
            viewHolder.txtEdad = convertView.findViewById(R.id.txtedadContacto);
            viewHolder.txtNacionalidad = convertView.findViewById(R.id.txtnacionalidadContacto);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        User contact = contactList.get(position);

        viewHolder.txtNombre.setText("Nombre: " + contact.getNombreContacto());
        viewHolder.txtCorreo.setText("Correo: " + contact.getCorreoContacto());
        viewHolder.txtEdad.setText("Edad: " + contact.getEdadContacto());
        viewHolder.txtNacionalidad.setText("Nacionalidad: " + contact.getNacionalidadContacto());

        return convertView;
    }

    private static class ViewHolder {
        TextView txtNombre;
        TextView txtCorreo;
        TextView txtEdad;
        TextView txtNacionalidad;
    }
}

