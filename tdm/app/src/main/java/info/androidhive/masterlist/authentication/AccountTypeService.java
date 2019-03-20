package info.androidhive.masterlist.authentication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class AccountTypeService extends Service {
    private MasterListAuthenticator authenticator=new MasterListAuthenticator(this);
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
