package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteDao {
    /**
     * ����cid��ѯ�ܼ�¼��
     */
    public int findTotalCount (int cid, String rname);

    /**
     * ����cid��start��pa'geSize��ѯ��ǰҳ�����ݼ���
     */
    public List<Route> findByPage (int cid, int start, int pageSize, String rname);

    /**
     * ����id��route����
     *
     * @param rid
     * @return
     */
    public Route findOne (int rid);

}
