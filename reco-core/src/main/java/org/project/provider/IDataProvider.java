package org.project.provider;

import java.util.List;

public interface IDataProvider<Req, Res> {
    List<Res> getData(Req req);
}
