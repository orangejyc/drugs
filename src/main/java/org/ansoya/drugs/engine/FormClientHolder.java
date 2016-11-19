package org.ansoya.drugs.engine;


import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.concurrent.ConcurrentMap;

public abstract class FormClientHolder {
    private static final ConcurrentMap<String, FormClient> map = Maps.newConcurrentMap();

    public static void addFormClient(FormClient formClient) {
        Preconditions.checkArgument(null != formClient && !Strings.isNullOrEmpty(formClient.getUid()));
        map.put(formClient.getUid(), formClient);
    }

    public static void removeFormClient(FormClient formClient) {
        Preconditions.checkArgument(null != formClient && !Strings.isNullOrEmpty(formClient.getUid()));
        map.remove(formClient.getUid(), formClient);
    }

    public static boolean containsKey(String sik) {
        return map.containsKey(sik);
    }

    public static boolean containsKey(FormClient formClient) {
        Preconditions.checkArgument(null != formClient && !Strings.isNullOrEmpty(formClient.getUid()));
        return map.containsKey(formClient.getUid());
    }

    public static void clear() {
        map.clear();
    }

    public static FormClient getFormClientBySik(String sik) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(sik));
        return map.get(sik);
    }
}
