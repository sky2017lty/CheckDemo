package com.poshing.checkdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.poshing.checkdemo.dao.ChecklogDao;
import com.poshing.checkdemo.dao.FeedingcheckDao;
import com.poshing.checkdemo.dao.FeedingroupDao;
import com.poshing.checkdemo.entity.Checklog;
import com.poshing.checkdemo.entity.Feedingcheck;
import com.poshing.checkdemo.entity.Feedinggroup;
import com.poshing.checkdemo.service.CheckServices;
import com.poshing.checkdemo.utils.JsonUtils;
import com.poshing.checkdemo.utils.UUIDUtils;
import com.poshing.checkdemo.utils.Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author litianyi
 */
@Service
public class CheckServicesImpl implements CheckServices {

    @Resource
    private FeedingcheckDao feedingcheckDao;

    @Resource
    private ChecklogDao checklogDao;

    @Resource
    private FeedingroupDao groupDao;

    @Override
    public String checkData(HttpServletRequest request) {
        List<Feedingcheck> checkList = feedingcheckDao.selectList(new QueryWrapper<Feedingcheck>());
        return JsonUtils.getInstance().formatLayerJson(0, "success", checkList.size(), JSON.toJSONString(checkList));
    }

    @Override
    public String addFeedingCheck(HttpServletRequest request) {
        String feedingMes = request.getParameter("feeding_MES");
        String feedingNo = request.getParameter("feeding_no");
        String username = Utils.getSession(request, "name");
        boolean mes = Utils.isNull(feedingMes);
        boolean no = Utils.isNull(feedingNo);
        if (mes || no) {
            return JsonUtils.getInstance().formatLayerJson(200, "数据错误，请重新核对");
        }
        Feedingcheck selectOne = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", feedingMes));
        if (selectOne == null) {
            Feedingcheck one = new Feedingcheck();
            one.setUuid(UUIDUtils.getUuid());
            one.setFeedingMes(feedingMes);
            one.setFeedingNo(feedingNo);
            one.setUsername(username);
            one.setDate(Utils.getDate());
            one.setTime(Utils.getTime());
            int flag = feedingcheckDao.insert(one);
            return Utils.returnJson(flag);
        } else {
            return JsonUtils.getInstance().formatLayerJson(200, "MES已进站");
        }
    }

    @Override
    public String cutting(HttpServletRequest request) {
        String cuttingMes = request.getParameter("cutting_MES");
        String cuttingNo = request.getParameter("cutting_no");
        String username = Utils.getSession(request, "name");
        boolean mes = Utils.isNull(cuttingMes);
        boolean no = Utils.isNull(cuttingNo);
        if (mes || no) {
            return JsonUtils.getInstance().formatLayerJson(200, "数据错误，请重新核对");
        }
        Feedingcheck selectOne = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", cuttingMes));
        if (selectOne == null) {
            return JsonUtils.getInstance().formatLayerJson(200, "找不到MES");
        } else {
//            return insertLog(selectOne, cuttingMes, cuttingNo, cuttingMachine, username);
            return insertLog(selectOne, cuttingMes, cuttingNo, username);
        }
    }

    @Override
    public String getAllLog(HttpServletRequest request) {
        String start = request.getParameter("start");
        String end = request.getParameter("end");
        List<Checklog> checklogList;
        if (Utils.isNull(start) && Utils.isNull(end)) {
            checklogList = checklogDao.selectList(new QueryWrapper<Checklog>()
                    .orderByDesc("cutting_date", "cutting_time"));
        } else {
            checklogList = checklogDao.selectList(new QueryWrapper<Checklog>()
                    .ge("cutting_date", start)
                    .le("cutting_date", end)
                    .orderByDesc("cutting_date", "cutting_time"));
        }
        return JsonUtils.getInstance().formatLayerJson(0, "success", checklogList.size(), JSON.toJSONString(checklogList));
    }

    @Override
    public String addFeedingCheckGroup(HttpServletRequest request) {
        String groupno1 = request.getParameter("groupno_1");
        String feedingMes1 = request.getParameter("feeding_MES_1");
        String feedingNo1 = request.getParameter("feeding_no_1");
        String groupno2 = request.getParameter("groupno_2");
        String feedingMes2 = request.getParameter("feeding_MES_2");
        String feedingNo2 = request.getParameter("feeding_no_2");
        String groupno3 = request.getParameter("groupno_3");
        String feedingMes3 = request.getParameter("feeding_MES_3");
        String feedingNo3 = request.getParameter("feeding_no_3");
        String groupno4 = request.getParameter("groupno_4");
        String feedingMes4 = request.getParameter("feeding_MES_4");
        String feedingNo4 = request.getParameter("feeding_no_4");
        String groupno5 = request.getParameter("groupno_5");
        String feedingMes5 = request.getParameter("feeding_MES_5");
        String feedingNo5 = request.getParameter("feeding_no_5");
        String groupno6 = request.getParameter("groupno_6");
        String feedingMes6 = request.getParameter("feeding_MES_6");
        String feedingNo6 = request.getParameter("feeding_no_6");
        String username = Utils.getSession(request, "name");
        Feedinggroup feedingGroup = new Feedinggroup();
        feedingGroup.setUuid(UUIDUtils.getUuid());
        feedingGroup.setGroupTimestamp(Long.toString(System.currentTimeMillis()));
        int flag = groupDao.insert(feedingGroup);
        if (1 != flag) {
            return JsonUtils.getInstance().formatLayerJson(200, "failed");
        }
        //mes为空
        JSONObject feeding1 = addFeeding(feedingMes1, feedingNo1, username, feedingGroup.getUuid(), groupno1);
        JSONObject feeding2 = addFeeding(feedingMes2, feedingNo2, username, feedingGroup.getUuid(), groupno2);
        JSONObject feeding3 = addFeeding(feedingMes3, feedingNo3, username, feedingGroup.getUuid(), groupno3);
        JSONObject feeding4 = addFeeding(feedingMes4, feedingNo4, username, feedingGroup.getUuid(), groupno4);
        JSONObject feeding5 = addFeeding(feedingMes5, feedingNo5, username, feedingGroup.getUuid(), groupno5);
        JSONObject feeding6 = addFeeding(feedingMes6, feedingNo6, username, feedingGroup.getUuid(), groupno6);
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(feeding1);
        jsonArray.add(feeding2);
        jsonArray.add(feeding3);
        jsonArray.add(feeding4);
        jsonArray.add(feeding5);
        jsonArray.add(feeding6);
        for (Object o : jsonArray) {
            JSONObject json = (JSONObject) o;
            if ((int) json.get("code") == 202) {
                return JsonUtils.getInstance().formatLayerJson(202, json.get("feedingMes") + "MES已存在");
            }
        }
        return JsonUtils.getInstance().formatLayerJson(0, "success");
    }

    private JSONObject addFeeding(String feedingMes, String feedingNo, String username, String group, String groupNo) {
        JSONObject result = new JSONObject();
        boolean mes = Utils.isNull(feedingMes);
        boolean no = Utils.isNull(feedingNo);
        if (mes || no) {
            result.put("code", 200);
            result.put("msg", "没有数据");
            return result;
        }
        Feedingcheck selectOne = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", feedingMes));
        if (selectOne == null) {
            Feedingcheck one = new Feedingcheck();
            one.setUuid(UUIDUtils.getUuid());
            one.setFeedingMes(feedingMes);
            one.setFeedingNo(feedingNo);
            one.setUsername(username);
            one.setFeedingGroup(group);
            one.setFeedingGroupno(groupNo);
            one.setDate(Utils.getDate());
            one.setTime(Utils.getTime());
            int flag = feedingcheckDao.insert(one);
            if (1 == flag) {
                result.put("code", 0);
                result.put("msg", "success");
            } else {
                result.put("code", 201);
                result.put("msg", "failed");
            }
            return result;
        } else {
            result.put("code", 202);
            result.put("feedingMes", feedingMes);
            result.put("msg", "MES已存在");
            return result;
        }
    }


    private String insertLog(Feedingcheck feedingcheck, String cuttingMes, String cuttingNo, String cuttingUsername) {
        Checklog checklog = new Checklog();
        checklog.setUuid(UUIDUtils.getUuid());
        checklog.setFeedingUsername(feedingcheck.getUsername());
        checklog.setFeedingDate(feedingcheck.getDate());
        checklog.setFeedingTime(feedingcheck.getTime());
        checklog.setFeedingMes(feedingcheck.getFeedingMes());
        checklog.setFeedingNo(feedingcheck.getFeedingNo());
        checklog.setCuttingUsername(cuttingUsername);
        checklog.setCuttingDate(Utils.getDate());
        checklog.setCuttingTime(Utils.getTime());
        checklog.setCuttingMes(cuttingMes);
        checklog.setCuttingNo(cuttingNo);
        int flag = 0;
        int flagFeeding = 0;
        if (feedingcheck.getFeedingMes().equals(cuttingMes) && feedingcheck.getFeedingNo().equals(cuttingNo)) {
            checklog.setResult("正确");
            flagFeeding = feedingcheckDao.deleteById(feedingcheck);
        } else {
            checklog.setResult("错误");
        }
        int flagCutting = checklogDao.insert(checklog);
        if (flagFeeding == 1 && flagCutting == 1) {
            flag = 1;
        }
        return Utils.returnJson(flag);
    }
}
