package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgImpl();
    private SellerDao sellerdao = new SellerDaoImpl();

    @Override
    public Route findOne(String rid) {
        // 1.����idȥroute���в�ѯroute����
        Route route = routeDao.findOne(Integer.parseInt(rid));
        // 2.1����route��id ��ѯͼƬ������Ϣ
        List<RouteImg> routeImgList = routeImgDao.findByRid(route.getRid());
        // 2.2���������õ�route����
        route.setRouteImgList(routeImgList);
        Seller seller = sellerdao.findBySid(route.getSid());
        route.setSeller(seller);
        return route;
    }

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        // ��װbean����
        PageBean<Route> pb = new PageBean<Route>();
        // ���õ�ǰҳ�� currentPage
        pb.setCurrentPage(currentPage);
        // ����ÿҳ��ʾ���� pageSize
        pb.setPageSize(pageSize);
        // �����ܼ�¼��  totalCount
        int totalCount = routeDao.findTotalCount(cid, rname);
        pb.setTotalCount(totalCount);
        // ���õ�ǰҳ����ʾ�����ݼ���
        int start = (currentPage - 1) * pageSize; // ��ʼ�ļ�¼��
        List<Route> list = routeDao.findByPage(cid, start, pageSize, rname);
        pb.setList(list);
        // ������ҳ��=�ܼ�¼��/ÿҳ��ʾ����  ����������+1
        int totalPage =
                totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pb.setTotalPage(totalPage);
        return pb;
    }
}
