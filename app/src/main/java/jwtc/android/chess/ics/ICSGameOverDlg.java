package jwtc.android.chess.ics;

import jwtc.android.chess.*;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ICSGameOverDlg extends Dialog {

    public static final String TAG = "ICSGameOverDlg";

    private ICSClient _parent;
    private Button _butGoodGame, _butRematch, _butExamine, _butClipBoard, _butSend, _butExit;
    private TextView _tvGameResult, _tvSendMessagesTitle;


    public ICSGameOverDlg(Context context) {
        super(context);

        _parent = (ICSClient)context;

        setContentView(R.layout.ics_over);

        setTitle("Game Over");

        _butExit = (Button)findViewById(R.id.ButtonGameExit);
        _butExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ICSGameOverDlg.this.dismiss();
            }
        });

        _tvGameResult = (TextView)findViewById(R.id.tvGameResult);
        _tvGameResult.setGravity(Gravity.CENTER);

        _tvSendMessagesTitle= (TextView)findViewById(R.id.textView);

        _butGoodGame = (Button)findViewById(R.id.ButtonGameGoodGame);
        _butGoodGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _parent.sendString("tell " + _parent.get_view().getOpponent() + " Good Game!");
                _parent.gameToast("You told " + _parent.get_view().getOpponent() + ", \"Good Game!\"", false);
            }
        });

        _butRematch = (Button)findViewById(R.id.ButtonGameRematch);
        _butRematch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                _parent.sendString("rematch");
            }
        });

        _butExamine = (Button)findViewById(R.id.ButtonGameExamine);
        _butExamine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    _parent.sendString("examine " + _parent.get_whiteHandle() + " -1");
                } catch (Exception e){
                    _parent.doToast(e.toString());
                    Log.e(TAG, "Exception error ->" + e.toString());
                }

            }
        });

        _butClipBoard = (Button)findViewById(R.id.ButtonGameClipBoard);
        _butClipBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _parent.copyToClipBoard();
            }
        });

        _butSend = (Button)findViewById(R.id.ButtonGameSend);
        _butSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _parent.SendToApp();
            }
        });
    }

    public void setWasPlaying(boolean bWasPlaying){
        _tvSendMessagesTitle.setVisibility((bWasPlaying ? View.VISIBLE : View.GONE));
        _butGoodGame.setVisibility(bWasPlaying ? View.VISIBLE : View.GONE);
        _butRematch.setVisibility(bWasPlaying ? View.VISIBLE : View.GONE);
    }

    public void updateGameResultText(String GRText){
        _tvGameResult.setText(GRText);
    }
}
