package com.ioudebtcalculator.newaccount;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.ioudebtcalculator.App;
import com.ioudebtcalculator.R;

import java.util.Calendar;

import javax.inject.Inject;

public class NewAccountFragment extends Fragment implements NewAccountView {

    private static final int CONTACT_PICKER_RESULT = 0;
    private static final String KEY_PHOTO_URI = "key_photo_uri";
    private static final String KEY_CURRENT_DUE_DATE = "key_current_due_date";

    @Inject NewAccountPresenter presenter;

    private RadioGroup rdgBorrowOrLoan;
    private RadioButton rdbBorrow;
    private RadioButton rdbLoan;
    private EditText edtAccountName;
    private ImageButton btnAddContact;
    private EditText edtAmount;
    private Spinner spnCurrency;
    private EditText edtDescription;
    private ImageView imgAccountImage;
    private CheckBox chkDueDate;
    private TextView txtCurrentDueDate;
    private ImageButton btnChooseDueDate;
    private FloatingActionButton btnSaveAccount;

    private String photoUri;
    private long currentDueDate = 0L;

    private View.OnClickListener saveAccountListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.validateInputAndSave();
        }
    };

    private View.OnClickListener clearRadioErrorListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            rdbBorrow.setError(null);
            rdbLoan.setError(null);
        }
    };

    private View.OnClickListener pickContactListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                    ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
        }
    };

    private DatePickerDialog.OnDateSetListener onDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar dueDate = Calendar.getInstance();
            dueDate.set(Calendar.YEAR, year);
            dueDate.set(Calendar.MONTH, monthOfYear);
            dueDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            java.text.DateFormat dateFormat = DateFormat.getDateFormat(getActivity());
            currentDueDate = dueDate.getTimeInMillis();
            txtCurrentDueDate.setText(dateFormat.format(dueDate.getTime()));
        }
    };

    private View.OnClickListener pickDueDateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v instanceof CheckBox) {
                if (!((CheckBox) v).isChecked()) {
                    return;
                }
            }
            DueDatePickerDialog dueDatePickerDialog = new DueDatePickerDialog();
            dueDatePickerDialog.setListener(onDateSetListener);
            dueDatePickerDialog.show(getFragmentManager(), "due_date_tag");
        }
    };

    private EditText.OnEditorActionListener doneActionListener =
            new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                btnSaveAccount.performClick();
                return true;
            }
            return false;
        }
    };

    public static NewAccountFragment newInstance() {
        return new NewAccountFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_account, container, false);

        rdgBorrowOrLoan = (RadioGroup) view.findViewById(R.id.rdgBorrowOrLoan);
        rdbBorrow = (RadioButton) view.findViewById(R.id.rdbBorrow);
        rdbLoan = (RadioButton) view.findViewById(R.id.rdbLoan);
        edtAccountName = (EditText) view.findViewById(R.id.edtAccountName);
        btnAddContact = (ImageButton) view.findViewById(R.id.btnAddContact);
        edtAmount = (EditText) view.findViewById(R.id.edtAmount);
        spnCurrency = (Spinner) view.findViewById(R.id.spnCurrency);
        edtDescription = (EditText) view.findViewById(R.id.edtDescription);
        imgAccountImage = (ImageView) view.findViewById(R.id.imgAccountImage);
        chkDueDate = (CheckBox) view.findViewById(R.id.chkDueDate);
        txtCurrentDueDate = (TextView) view.findViewById(R.id.txtCurrentDueDate);
        btnChooseDueDate = (ImageButton) view.findViewById(R.id.btnChooseDueDate);
        btnSaveAccount = (FloatingActionButton) view.findViewById(R.id.saveAccountFab);

        btnSaveAccount.setOnClickListener(saveAccountListener);
        rdbBorrow.setOnClickListener(clearRadioErrorListener);
        rdbLoan.setOnClickListener(clearRadioErrorListener);
        btnAddContact.setOnClickListener(pickContactListener);
        edtDescription.setOnEditorActionListener(doneActionListener);
        chkDueDate.setOnClickListener(pickDueDateListener);
        btnChooseDueDate.setOnClickListener(pickDueDateListener);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spnCurrency.setAdapter(new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.available_currencies)
        ));
        //TODO: Pull these currencies from Preferences.
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (photoUri != null) {
            outState.putString(KEY_PHOTO_URI, photoUri);
        }
        if (currentDueDate != 0f) {
            outState.putLong(KEY_CURRENT_DUE_DATE, currentDueDate);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            photoUri = savedInstanceState.getString(KEY_PHOTO_URI);
            if (photoUri != null) {
                imgAccountImage.setImageURI(Uri.parse(photoUri));
            }
            currentDueDate = savedInstanceState.getLong(KEY_CURRENT_DUE_DATE, 0L);
            if (currentDueDate != 0L) {
                java.text.DateFormat dateFormat = DateFormat.getDateFormat(getActivity());
                Calendar dueDate = Calendar.getInstance();
                dueDate.setTimeInMillis(currentDueDate);
                txtCurrentDueDate.setText(dateFormat.format(dueDate.getTime()));
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CONTACT_PICKER_RESULT) {
                Uri contactUri = data.getData();
                Cursor contactCursor = getActivity()
                        .getContentResolver()
                        .query(
                                contactUri,
                                new String[] {
                                        ContactsContract.Contacts.DISPLAY_NAME,
                                        ContactsContract.Contacts.PHOTO_URI
                                },
                                null,
                                null,
                                null
                        );
                if (contactCursor != null && contactCursor.moveToFirst()) {
                    int displayNameColumn = contactCursor
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    int photoUriColumn = contactCursor
                            .getColumnIndex(ContactsContract.Contacts.PHOTO_URI);
                    String displayName = contactCursor.getString(displayNameColumn);
                    photoUri = contactCursor.getString(photoUriColumn);
                    contactCursor.close();
                    if (displayName != null) {
                        edtAccountName.setText(displayName);
                    }
                    if (photoUri != null) {
                        imgAccountImage.setImageURI(Uri.parse(photoUri));
                    } else {
                        imgAccountImage.setImageURI(null);
                    }
                }
            }
        }
    }

    @Override
    public void close() {
        getActivity().setResult(Activity.RESULT_OK);
        getActivity().finish();
    }

    @Override
    public int getBorrowOrLoanCheckedId() {
        return rdgBorrowOrLoan.getCheckedRadioButtonId();
    }

    @Override
    public String getAmountEntered() {
        return edtAmount.getText().toString();
    }

    @Override
    public String getNameEntered() {
        return edtAccountName.getText().toString();
    }

    @Override
    public String getCurrencyCode() {
        return spnCurrency.getSelectedItem().toString();
    }

    @Override
    public String getDescriptionEntered() {
        return edtDescription.getText().toString();
    }

    @Override
    public void setAmountError() {
        edtAmount.setError(getResources()
            .getString(R.string.error_must_enter_amount));
    }

    @Override
    public void setBorrowOrLoanError() {
        String error = getResources().getString(R.string.error_borrow_or_loan);
        rdbBorrow.setError(error);
        rdbLoan.setError(error);
    }

    @Override
    public void setAccountNameError() {
        edtAccountName.setError(getResources().getString(R.string.error_must_enter_account_name));
    }

    @Override
    public String getImageUri() {
        return photoUri;
    }

    @Override
    public long getDueDate() {
        return currentDueDate;
    }
}
