package com.example.mahila;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class EmergencyContactsActivity extends AppCompatActivity {

    private AppPreferences preferences;
    private TextInputLayout nameLayout;
    private TextInputLayout phoneLayout;
    private TextInputEditText nameInput;
    private TextInputEditText phoneInput;
    private MaterialButton saveButton;
    private LinearLayout contactsContainer;
    private TextView emptyText;
    private String editingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_emergency_contacts);

        View root = findViewById(R.id.emergency_contacts_root);
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.emergency_contacts_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Emergency Contacts");
        }
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());

        preferences = new AppPreferences(this);
        nameLayout = findViewById(R.id.layout_contact_name);
        phoneLayout = findViewById(R.id.layout_contact_phone);
        nameInput = findViewById(R.id.input_contact_name);
        phoneInput = findViewById(R.id.input_contact_phone);
        saveButton = findViewById(R.id.button_save_contact);
        contactsContainer = findViewById(R.id.saved_contacts_container);
        emptyText = findViewById(R.id.text_no_contacts);

        findViewById(R.id.card_national_emergency).setOnClickListener(v -> dialNumber("112"));
        findViewById(R.id.card_women_helpline).setOnClickListener(v -> dialNumber("1091"));
        findViewById(R.id.card_ambulance).setOnClickListener(v -> dialNumber("108"));

        saveButton.setOnClickListener(v -> saveContact());
    }

    @Override
    protected void onResume() {
        super.onResume();
        showContacts();
    }

    private void saveContact() {
        nameLayout.setError(null);
        phoneLayout.setError(null);

        String name = textOf(nameInput);
        String phone = textOf(phoneInput);

        if (name.length() < 2) {
            nameLayout.setError("Enter a name with at least 2 characters.");
            return;
        }
        if (!name.matches("[a-zA-Z .']+")) {
            nameLayout.setError("Use letters only in the name.");
            return;
        }

        String digits = phone.replaceAll("\\D", "");
        if (digits.length() < 10 || digits.length() > 15) {
            phoneLayout.setError("Enter a valid phone number with 10 to 15 digits.");
            return;
        }

        List<AppPreferences.EmergencyContact> contacts = preferences.getContacts();
        for (AppPreferences.EmergencyContact contact : contacts) {
            boolean sameNumber = contact.phone.replaceAll("\\D", "").equals(digits);
            boolean differentRecord = editingId == null || !contact.id.equals(editingId);
            if (sameNumber && differentRecord) {
                phoneLayout.setError("This phone number is already saved.");
                return;
            }
        }

        if (editingId == null) {
            String id = String.valueOf(System.currentTimeMillis());
            contacts.add(new AppPreferences.EmergencyContact(id, name, phone));
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
        } else {
            boolean updated = false;
            for (AppPreferences.EmergencyContact contact : contacts) {
                if (contact.id.equals(editingId)) {
                    contact.name = name;
                    contact.phone = phone;
                    updated = true;
                    break;
                }
            }
            if (!updated) {
                contacts.add(new AppPreferences.EmergencyContact(editingId, name, phone));
            }
            Toast.makeText(this, "Contact updated", Toast.LENGTH_SHORT).show();
            editingId = null;
            saveButton.setText("Save contact");
        }

        preferences.saveContacts(contacts);
        nameInput.setText("");
        phoneInput.setText("");
        showContacts();
    }

    private void showContacts() {
        contactsContainer.removeAllViews();
        List<AppPreferences.EmergencyContact> contacts = preferences.getContacts();
        emptyText.setVisibility(contacts.isEmpty() ? View.VISIBLE : View.GONE);

        LayoutInflater inflater = LayoutInflater.from(this);
        for (AppPreferences.EmergencyContact contact : contacts) {
            View item = inflater.inflate(R.layout.item_emergency_contact, contactsContainer, false);
            TextView nameView = item.findViewById(R.id.text_contact_name);
            TextView phoneView = item.findViewById(R.id.text_contact_phone);
            MaterialButton editButton = item.findViewById(R.id.button_edit_contact);
            MaterialButton deleteButton = item.findViewById(R.id.button_delete_contact);

            nameView.setText(contact.name);
            phoneView.setText(contact.phone);

            item.setOnClickListener(v -> dialNumber(contact.phone));
            editButton.setOnClickListener(v -> startEdit(contact));
            deleteButton.setOnClickListener(v -> deleteContact(contact.id));
            contactsContainer.addView(item);
        }
    }

    private void startEdit(AppPreferences.EmergencyContact contact) {
        editingId = contact.id;
        nameLayout.setError(null);
        phoneLayout.setError(null);
        nameInput.setText(contact.name);
        phoneInput.setText(contact.phone);
        saveButton.setText("Update contact");
        nameInput.requestFocus();
    }

    private void deleteContact(String id) {
        List<AppPreferences.EmergencyContact> contacts = preferences.getContacts();
        for (int i = contacts.size() - 1; i >= 0; i--) {
            if (contacts.get(i).id.equals(id)) {
                contacts.remove(i);
            }
        }
        preferences.saveContacts(contacts);

        if (id.equals(editingId)) {
            editingId = null;
            nameInput.setText("");
            phoneInput.setText("");
            saveButton.setText("Save contact");
        }

        showContacts();
        Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show();
    }

    private void dialNumber(String number) {
        String digits = number.replaceAll("\\D", "");
        if (digits.isEmpty()) {
            Toast.makeText(this, "This number cannot be dialed.", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + digits));
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No phone app is available on this device.", Toast.LENGTH_SHORT).show();
        }
    }

    private String textOf(TextInputEditText input) {
        return input.getText() == null ? "" : input.getText().toString().trim();
    }
}
