package Observers;

import Controllers.Interfaces.IScoresUploadNotifier;

import java.util.ArrayList;
import java.util.List;

public class ScoresUploadObserver {

    List<IScoresUploadNotifier> notifierList = new ArrayList<>();

    public void subscribeNotifier(IScoresUploadNotifier notifier){
        notifierList.add(notifier);
    }

    public void notifyNotifiers(){
        for(IScoresUploadNotifier notifier : notifierList){
            notifier.onNotifyScoreUpload();
        }
    }
}
