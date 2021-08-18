package com.poshing.checkdemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        List<Feedingcheck> checkList = feedingcheckDao.selectList(new QueryWrapper<Feedingcheck>()
                .orderByAsc("feeding_timestamp", "feeding_groupno"));
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
        List<Object> one = groupDao.selectObjs(new QueryWrapper<Feedinggroup>().select("min(group_timestamp)"));
        if (one == null) {
            return JsonUtils.getInstance().formatLayerJson(200, "找不到数据");
        }
        String timestamp = (String) one.get(0);
        Feedingcheck selectOne = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", cuttingMes)
                .eq("feeding_timestamp", timestamp));
        if (selectOne == null) {
            return JsonUtils.getInstance().formatLayerJson(200, "最早组中找不到该MES");
        } else {
            String result;
            if (selectOne.getFeedingMes().equals(cuttingMes) && selectOne.getFeedingNo().equals(cuttingNo)) {
                result = insertLog(selectOne, cuttingMes, cuttingNo, "正确", username);
            } else {
                result = insertLog(selectOne, cuttingMes, cuttingNo, "错误", username);
            }
            List<Feedingcheck> selectList = feedingcheckDao.selectList(new QueryWrapper<Feedingcheck>().eq("feeding_timestamp", timestamp));
            if (selectList.size() == 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("group_timestamp", timestamp);
                int flag = groupDao.deleteByMap(map);
                return Utils.returnJson(flag);
            }
            return result;
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
        String groupno1 = request.getParameter("groupno_1").toUpperCase();
        String feedingMes1 = request.getParameter("feeding_MES_1").toUpperCase();
        String feedingNo1 = request.getParameter("feeding_no_1").toUpperCase();
        String groupno2 = request.getParameter("groupno_2").toUpperCase();
        String feedingMes2 = request.getParameter("feeding_MES_2").toUpperCase();
        String feedingNo2 = request.getParameter("feeding_no_2").toUpperCase();
        String groupno3 = request.getParameter("groupno_3").toUpperCase();
        String feedingMes3 = request.getParameter("feeding_MES_3").toUpperCase();
        String feedingNo3 = request.getParameter("feeding_no_3").toUpperCase();
        String groupno4 = request.getParameter("groupno_4").toUpperCase();
        String feedingMes4 = request.getParameter("feeding_MES_4").toUpperCase();
        String feedingNo4 = request.getParameter("feeding_no_4").toUpperCase();
        String groupno5 = request.getParameter("groupno_5").toUpperCase();
        String feedingMes5 = request.getParameter("feeding_MES_5").toUpperCase();
        String feedingNo5 = request.getParameter("feeding_no_5").toUpperCase();
        String groupno6 = request.getParameter("groupno_6").toUpperCase();
        String feedingMes6 = request.getParameter("feeding_MES_6").toUpperCase();
        String feedingNo6 = request.getParameter("feeding_no_6").toUpperCase();
        String username = Utils.getSession(request, "name");
        boolean mesFlag = checkStringEqual(feedingMes1, feedingMes2, feedingMes3, feedingMes4, feedingMes5, feedingMes6);
        boolean noFlag = checkStringEqual(feedingNo1, feedingNo2, feedingNo3, feedingNo4, feedingNo5, feedingNo6);
        if (mesFlag || noFlag) {
            return JsonUtils.getInstance().formatLayerJson(200, "MES出现重复");
        }
        Feedingcheck mes1 = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", feedingMes1));
        Feedingcheck mes2 = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", feedingMes2));
        Feedingcheck mes3 = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", feedingMes3));
        Feedingcheck mes4 = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", feedingMes4));
        Feedingcheck mes5 = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", feedingMes5));
        Feedingcheck mes6 = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", feedingMes6));

        if (mes1 != null || mes2 != null ||
                mes3 != null || mes4 != null ||
                mes5 != null || mes6 != null) {
            return JsonUtils.getInstance().formatLayerJson(200, "MES出现重复");
        }

        Feedinggroup feedingGroup = new Feedinggroup();
        feedingGroup.setUuid(UUIDUtils.getUuid());
        feedingGroup.setGroupTimestamp(Long.toString(System.currentTimeMillis()));
        int flag = groupDao.insert(feedingGroup);
        if (1 != flag) {
            return JsonUtils.getInstance().formatLayerJson(200, "failed");
        }
        //mes为空
        JSONObject feeding1 = addFeeding(feedingMes1, feedingNo1, username, feedingGroup.getUuid(), groupno1, feedingGroup.getGroupTimestamp());
        JSONObject feeding2 = addFeeding(feedingMes2, feedingNo2, username, feedingGroup.getUuid(), groupno2, feedingGroup.getGroupTimestamp());
        JSONObject feeding3 = addFeeding(feedingMes3, feedingNo3, username, feedingGroup.getUuid(), groupno3, feedingGroup.getGroupTimestamp());
        JSONObject feeding4 = addFeeding(feedingMes4, feedingNo4, username, feedingGroup.getUuid(), groupno4, feedingGroup.getGroupTimestamp());
        JSONObject feeding5 = addFeeding(feedingMes5, feedingNo5, username, feedingGroup.getUuid(), groupno5, feedingGroup.getGroupTimestamp());
        JSONObject feeding6 = addFeeding(feedingMes6, feedingNo6, username, feedingGroup.getUuid(), groupno6, feedingGroup.getGroupTimestamp());
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

    @Override
    public String getLastGroup(HttpServletRequest request) {
//        List<Object> one = groupDao.selectObjs(new QueryWrapper<Feedinggroup>().select("max(group_timestamp)"));
        List<Object> one = groupDao.selectObjs(new QueryWrapper<Feedinggroup>().select("min(group_timestamp)"));
        if (one == null) {
            return JsonUtils.getInstance().formatLayerJson(200, "找不到数据");
        }
        String timestamp = (String) one.get(0);
        List<Feedingcheck> selectList = feedingcheckDao.selectList(new QueryWrapper<Feedingcheck>().eq("feeding_timestamp", timestamp).orderByAsc("feeding_groupno"));
        return JsonUtils.getInstance().formatLayerJson(0, "success", selectList.size(), JSON.toJSONString(selectList));
    }

    @Override
    public String setWarning(HttpServletRequest request) {
        String warning1 = request.getParameter("warning_1");
        String warning2 = request.getParameter("warning_2");
        String warning3 = request.getParameter("warning_3");
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
        String groupId = request.getParameter("groupId");
        String username = Utils.getSession(request, "name");
        if (checkWarning(warning3)) {
            cutting2(feedingMes5, feedingNo5, feedingMes6, feedingNo6, username);
        }
        if (checkWarning(warning2)) {
            cutting2(feedingMes3, feedingNo3, feedingMes4, feedingNo4, username);
            Feedingcheck feedingcheck = new Feedingcheck();
            feedingcheck.setFeedingGroupno("3");
            feedingcheck.setFeedingNo(feedingNo5);
            feedingcheckDao.update(feedingcheck, new UpdateWrapper<Feedingcheck>()
                    .eq("feeding_MES", feedingMes5));
            feedingcheck.setFeedingGroupno("4");
            feedingcheck.setFeedingNo(feedingNo6);
            feedingcheckDao.update(feedingcheck, new UpdateWrapper<Feedingcheck>()
                    .eq("feeding_MES", feedingMes6));
        }
        if (checkWarning(warning1)) {
            cutting2(feedingMes1, feedingNo1, feedingMes2, feedingNo2, username);
            Feedingcheck feedingcheck = new Feedingcheck();
            feedingcheck.setFeedingGroupno("1");
            feedingcheck.setFeedingNo(feedingNo3);
            int mes3 = feedingcheckDao.update(feedingcheck, new UpdateWrapper<Feedingcheck>()
                    .eq("feeding_MES", feedingMes3));
            feedingcheck.setFeedingGroupno("2");
            feedingcheck.setFeedingNo(feedingNo4);
            int mes4 = feedingcheckDao.update(feedingcheck, new UpdateWrapper<Feedingcheck>()
                    .eq("feeding_MES", feedingMes4));
            if (mes3 == 1 && mes4 == 1) {
                feedingcheck.setFeedingGroupno("3");
                feedingcheck.setFeedingNo(feedingNo5);
                feedingcheckDao.update(feedingcheck, new UpdateWrapper<Feedingcheck>()
                        .eq("feeding_MES", feedingMes5));
                feedingcheck.setFeedingGroupno("4");
                feedingcheck.setFeedingNo(feedingNo6);
                feedingcheckDao.update(feedingcheck, new UpdateWrapper<Feedingcheck>()
                        .eq("feeding_MES", feedingMes6));
            } else {
                feedingcheck.setFeedingGroupno("1");
                feedingcheck.setFeedingNo(feedingNo5);
                feedingcheckDao.update(feedingcheck, new UpdateWrapper<Feedingcheck>()
                        .eq("feeding_MES", feedingMes5));
                feedingcheck.setFeedingGroupno("2");
                feedingcheck.setFeedingNo(feedingNo6);
                feedingcheckDao.update(feedingcheck, new UpdateWrapper<Feedingcheck>()
                        .eq("feeding_MES", feedingMes6));
            }

            List<Feedingcheck> selectList = feedingcheckDao.selectList(new QueryWrapper<Feedingcheck>().eq("feeding_group", groupId));
            if (selectList.size() == 0) {
                int flag = groupDao.deleteById(groupId);
                return Utils.returnJson(flag);
            }
        }
        return JsonUtils.getInstance().formatLayerJson(0, "success");
    }

    private void cutting2(String cuttingMes1, String cuttingNo1,
                          String cuttingMes2, String cuttingNo2, String username) {
        Feedingcheck one1 = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", cuttingMes1)
                .eq("feeding_no", cuttingNo1));
        Feedingcheck one2 = feedingcheckDao.selectOne(new QueryWrapper<Feedingcheck>()
                .eq("feeding_MES", cuttingMes2)
                .eq("feeding_no", cuttingNo2));
        if (one1 != null) {
            insertLog(one1, cuttingMes1, cuttingNo1, "报警", username);
        }
        if (one2 != null) {
            insertLog(one2, cuttingMes2, cuttingNo2, "报警", username);
        }
    }

    private boolean checkWarning(String warning) {
        return "1".equals(warning);
    }

    private JSONObject addFeeding(String feedingMes, String feedingNo, String username, String group, String groupNo, String groupTimeStamp) {
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
            one.setFeedingTimestamp(groupTimeStamp);
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


    private String insertLog(Feedingcheck feedingcheck, String cuttingMes, String cuttingNo, String result, String cuttingUsername) {
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
        checklog.setResult(result);
        int flag = 0;
        int flagFeeding = 0;
        if (feedingcheck.getFeedingMes().equals(cuttingMes) && feedingcheck.getFeedingNo().equals(cuttingNo)) {
            flagFeeding = feedingcheckDao.deleteById(feedingcheck);
        }
        int flagCutting = checklogDao.insert(checklog);
        if (flagFeeding == 1 && flagCutting == 1) {
            flag = 1;
        }
        return Utils.returnJson(flag);
    }

    private boolean checkStringEqual(String... str) {
        for (int i = 0; i < str.length; i++) {
            if (!Utils.isNull(str[i])) {
                for (int j = i + 1; j < str.length; j++) {
                    if (str[i].equals(str[j])) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
