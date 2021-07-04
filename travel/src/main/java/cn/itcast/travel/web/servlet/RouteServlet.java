package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * @author huan.zhou
 */
@WebServlet ("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService service = new RouteServiceImpl();

    /**
     * ��ҳ��ѯ
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /**
         * ��ȡ����
         * currentPage ��ǰҳ��
         * pageSize ÿҳ��ʾ������
         * cid ��ҳid
         * rname ��·����
         */
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        //���ñ��룬��ֹ����
        rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        //�������������������ת��Ϊint
        int cid = 0;
        if (cidStr != null && cidStr.length() > 0) {
            cid = Integer.parseInt(cidStr);
        }
        //��ǰҳ�룬���ֵ�����ݣ�Ĭ��Ϊ1
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        } else {
            currentPage = 1;
        }
        //ÿҳ��ʾ�����������ֵ�����ݣ�Ĭ��Ϊ5
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        } else {
            pageSize = 5;
        }
        PageBean<Route> pb = service.pageQuery(cid, currentPage, pageSize, rname);
        //������Ķ������л�json��д�ؿͻ���
        writeValue(pb, response);
    }

    /**
     * ����rid��ѯ������·����ϸ��Ϣ
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.����id
        String rid = request.getParameter("rid");
        //2.����service��ѯ
        Route route = service.findOne(rid);
        //3.json����
        writeValue(route, response);
    }
}
