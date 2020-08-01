package com.example.mytravel.ui.booktour.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.booktour.BookTour;
import com.example.mytravel.ui.frame.FrameActivity;
import com.example.mytravel.ui.main.MainActivity;
import com.example.mytravel.utils.CommonUtils;
import com.google.gson.Gson;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.mytravel.utils.ConstApp.KEY_BOOK_TOUR;
import static com.example.mytravel.utils.ConstApp.KEY_CHECK_HISTORY;
import static com.example.mytravel.utils.ConstApp.KEY_NAME_TOUR;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_CITY;
import static com.example.mytravel.utils.ConstApp.KEY_TYPE_ID_EXPLORE;
import static com.example.mytravel.utils.ConstApp.PAYPAL_CLIENT_ID;
import static com.example.mytravel.utils.ConstApp.PAYPAL_REQUEST_CODE;

public class PaymentFragment extends BaseFragment implements PaymentFrMvpView, View.OnClickListener {
    public static final String TAG = PaymentFragment.class.getSimpleName();

    private PaymentFrMvpPresenter presenter;

    @BindView(R.id.ivCompany)
    CircleImageView ivCompany;
    @BindView(R.id.tvCustomer)
    TextView tvCustomer;
    @BindView(R.id.tvEmailCustomer)
    TextView tvEmailCustomer;
    @BindView(R.id.tvPhoneCustomer)
    TextView tvPhoneCustomer;
    @BindView(R.id.tvNameTour)
    TextView tvNameTour;
    @BindView(R.id.tvCheckInCheckOut)
    TextView tvCheckInCheckOut;
    @BindView(R.id.tvAdultsNumber)
    TextView tvAdultsNumber;
    @BindView(R.id.tvChildrenNumber)
    TextView tvChildrenNumber;
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.btnPay)
    Button btnPay;
    @BindView(R.id.tvPayed)
    TextView tvPayed;

    private BookTour bookTour;
    private boolean isHistory;
    private TextView tvTitle;
    private String idCity, idExplore;
    private String urlCompany = "https://www.onlinelogomaker.com/blog/wp-content/uploads/2017/09/travel-logo-design.jpg";
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(PAYPAL_CLIENT_ID)
            .merchantName("MyTravel");

    public static PaymentFragment newInstance(BookTour bookTour, String idCity, String idExplore, boolean isHistory) {
        PaymentFragment paymentFragment = new PaymentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_BOOK_TOUR, bookTour);
        bundle.putBoolean(KEY_CHECK_HISTORY, isHistory);
        bundle.putString(KEY_TYPE_ID_CITY, idCity);
        bundle.putString(KEY_TYPE_ID_EXPLORE, idExplore);
        paymentFragment.setArguments(bundle);
        return paymentFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PaymentFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));
            getActivity().findViewById(R.id.ivBack).setOnClickListener(this);
            tvTitle = getActivity().findViewById(R.id.tvTitleFrame);
        }
        if (getArguments() != null) {
            bookTour = getArguments().getParcelable(KEY_BOOK_TOUR);
            idCity = getArguments().getString(KEY_TYPE_ID_CITY);
            idExplore = getArguments().getString(KEY_TYPE_ID_EXPLORE);
            isHistory = getArguments().getBoolean(KEY_CHECK_HISTORY);
        }
        CommonUtils.loadImage(getContext(), urlCompany, ivCompany);
        if (bookTour != null) {
            if (isHistory) {
                tvTitle.setText(getString(R.string.text_title_detail_history));
                tvPayed.setVisibility(View.VISIBLE);
                tvTotalPrice.setVisibility(View.INVISIBLE);
                btnPay.setVisibility(View.INVISIBLE);
            } else {
                tvTitle.setText(getString(R.string.text_pay));
                tvPayed.setVisibility(View.INVISIBLE);
                tvTotalPrice.setVisibility(View.VISIBLE);
                btnPay.setVisibility(View.VISIBLE);
            }

            if (!TextUtils.isEmpty(bookTour.getNameUser())) {
                tvCustomer.setText(bookTour.getNameUser());
            }
            if (!TextUtils.isEmpty(bookTour.getEmailUser())) {
                tvEmailCustomer.setText(bookTour.getEmailUser());
            }
            if (!TextUtils.isEmpty(bookTour.getPhoneUser())) {
                tvPhoneCustomer.setText(bookTour.getPhoneUser());
            }
            if (!TextUtils.isEmpty(bookTour.getNameTour())) {
                tvNameTour.setText(String.format("%s: %s", getString(R.string.text_title_name_tour), bookTour.getNameTour()));
            }
            if (!TextUtils.isEmpty(bookTour.getCheckIn()) && !TextUtils.isEmpty(bookTour.getCheckOut())) {
                tvCheckInCheckOut.setText(String.format("%s: %s-%s", getString(R.string.text_title_check_in_check_out), bookTour.getCheckIn(), bookTour.getCheckOut()));
            }
            if (!TextUtils.isEmpty(bookTour.getNumberAdults())) {
                tvAdultsNumber.setText(String.format("%s: %s", getString(R.string.text_title_number_adults), bookTour.getNumberAdults()));
            }
            if (!TextUtils.isEmpty(bookTour.getNumberChildren())) {
                tvChildrenNumber.setText(String.format("%s: %s", getString(R.string.text_title_number_children), bookTour.getNumberChildren()));
            }
            if (!TextUtils.isEmpty(bookTour.getPriceTour())) {
                tvTotalPrice.setText(String.format("%s $%s", getString(R.string.text_title_total_price), bookTour.getPriceTour()));
            }

        }
    }

    @Override
    public void onClick(View v) {
        if (getActivity() != null && getFragmentManager() != null) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            } else {
                getActivity().finish();
            }
        }
    }

    @OnClick(R.id.btnPay)
    public void onClickPay() {
        PayPalPayment payment = new PayPalPayment(BigDecimal.valueOf(Double.parseDouble(bookTour.getPriceTour())), "USD", bookTour.getNameTour(), PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        String status = confirm.toJSONObject().getJSONObject("response").getString("state");
                        if (status.equals("approved")) {
                            Toast.makeText(getContext(), "Pay success", Toast.LENGTH_SHORT).show();
                            presenter.pushBookTour(bookTour);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "Cancel Payment", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(getContext(), "Invalid Payment", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void successPush() {
        if (getActivity() != null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
