package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteService {

    /**
     * ���������з�ҳ��ѯ
     *
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    public PageBean<Route> pageQuery (int cid, int currentPage, int pageSize, String rname);

    /**
     * ����id��ѯ
     *
     * @param rid
     * @return
     */
    Route findOne (String rid);
}
