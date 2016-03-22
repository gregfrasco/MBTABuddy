package DataManagement;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by cruzj6 on 3/21/2016.
 */
public class LoadingDialogManager {

    private static LoadingDialogManager instance = null;
    private ProgressDialog pd;
    private String LoadingMessage = "Loading...";

    public static LoadingDialogManager getInstance()
    {
        if(instance == null)
            instance = new LoadingDialogManager();
        return instance;
    }

    public void SetLoadingMessage(String msg)
    {
        LoadingMessage = msg;
    }

    public void ShowLoading(Context cont)
    {
        if(pd!=null && pd.isShowing())
            pd.dismiss();
        pd = new ProgressDialog(cont);
        pd.setMessage(LoadingMessage);
        pd.setIndeterminate(true);
        pd.setCancelable(false);
        pd.show();
    }

    public void DismissLoading()
    {
        if(pd!=null && pd.isShowing()) {
            LoadingMessage = "Loading...";
            pd.dismiss();
        }
    }

}
