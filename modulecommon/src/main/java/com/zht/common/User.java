package com.zht.common;

import java.util.Date;



/**
 * 用户表【t_user】
 *
 * @author oucl
 * @version $Revision: 1.0 $, $Date: 2014-6-26 上午10:58:11 $
 */
public class User {
    private static final long serialVersionUID = 6222653660393452354L;

    /**
     * 昵称（zerobook）
     */
    private String nickname;
    /**
     * 头像url（zerobook）
     */
    private String avatar;
    /**
     * 邮箱（zerobook）
     */
    private String email;
    /**
     * 性别（0未知，1男，2女）（zerobook）
     */
    private SexType sex;
    /**
     * 生日（zerobook）
     */
    private Date birthday;
    /**
     * 学历（zerobook）（0未知、1小学、2初中、3高中、4中专、5大专、6本科、7硕士、8博士、9博士后）
     */
    private UserEducationEnum education;
    /**
     * 职业（zerobook）
     */
    private String profession;

    /**
     * 创建时间
     */
    private Date createtime;
    /**
     * 状态（0正常，1注销）
     */
    private int status;
    /**
     * 是否删除
     */
    private int isdeleted;
    /**
     * 备注
     */
    private String remark;

    /**
     * 用户活跃度
     */
    private int activeValue;

    ////////////////////////
    private String dingId;// 钉钉id（用户可修改）
    private int active;// 表示该用户是否激活了钉钉，1表示是, 0表示不是
    private int isAdmin;// 是否是企业的管理员, 1表示是, 0表示不是
    private int isBoss;// 是否为企业的老板, 1表示是, 0表示不是
    private String position;// 职位信息
    private String jobnumber;// 员工工号
    private Date modifytime;// 最后修改时间

    private String rolename;// 角色名
    private long deptId;// 部门ID
    private String deptName;// 部门名称
    private String originalAvatar;// 原始头像

    private String corpName;// 企业名称

    private float fundsMoney;// 企业会员余额
    private int corpStatus;// 企业状态 0在职，1离职

    private int themeNum; // 用户发帖数
    private Date collectTime;
    private int fansNum; // 关注用户的粉丝数目
    private String introduce; // 教师一句话介绍
    private int isListenCourse;// 是否听课账号
    private int reportNum;// 报名课程数
    private int listenNum;// 听课数
    private boolean isCorpVip;// 是企业用户，企业已购买年卡产品，且此用户可以免费购买企业产品

    public SexType getSex() {
        return sex;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public UserEducationEnum getEducation() {
        return education;
    }

    public void setEducation(UserEducationEnum education) {
        this.education = education;
    }

    /**
     * @return Returns the rolename.
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * @param rolename
     *            The rolename to set.
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    /**
     * @return Returns the deptName.
     */
    public String getDeptName() {
        return deptName;
    }

    /**
     * @param deptName
     *            The deptName to set.
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /**
     * @return Returns the deptId.
     */
    public long getDeptId() {
        return deptId;
    }

    /**
     * @param deptId
     *            The deptId to set.
     */
    public void setDeptId(long deptId) {
        this.deptId = deptId;
    }

    /**
     * @return Returns the createtime.
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * @param createtime
     *            The createtime to set.
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * @return Returns the dingId.
     */
    public String getDingId() {
        return dingId;
    }

    /**
     * @param dingId
     *            The dingId to set.
     */
    public void setDingId(String dingId) {
        this.dingId = dingId;
    }

    /**
     * @return Returns the active.
     */
    public int getActive() {
        return active;
    }

    /**
     * @param active
     *            The active to set.
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     * @return Returns the email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return Returns the avatar.
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar
     *            The avatar to set.
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return Returns the isBoss.
     */
    public int getIsBoss() {
        return isBoss;
    }

    /**
     * @param isBoss
     *            The isBoss to set.
     */
    public void setIsBoss(int isBoss) {
        this.isBoss = isBoss;
    }

    /**
     * @return Returns the position.
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position
     *            The position to set.
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return Returns the jobnumber.
     */
    public String getJobnumber() {
        return jobnumber;
    }

    /**
     * @param jobnumber
     *            The jobnumber to set.
     */
    public void setJobnumber(String jobnumber) {
        this.jobnumber = jobnumber;
    }

    /**
     * @return Returns the modifytime.
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * @param modifytime
     *            The modifytime to set.
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }



    /**
     * @param originalAvatar
     *            The originalAvatar to set.
     */
    public void setOriginalAvatar(String originalAvatar) {
        this.originalAvatar = originalAvatar;
    }

    /**
     * @return Returns the nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     *            The nickname to set.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }





    /**
     * @return Returns the birthday.
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     *            The birthday to set.
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }



    /**
     * @return Returns the profession.
     */
    public String getProfession() {
        return profession;
    }

    /**
     * @param profession
     *            The profession to set.
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * @return Returns the isdeleted.
     */
    public int getIsdeleted() {
        return isdeleted;
    }

    /**
     * @param isdeleted
     *            The isdeleted to set.
     */
    public void setIsdeleted(int isdeleted) {
        this.isdeleted = isdeleted;
    }

    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     *            The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return Returns the isAdmin.
     */
    public int getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin
     *            The isAdmin to set.
     */
    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * @return Returns the corpName.
     */
    public String getCorpName() {
        return corpName;
    }

    /**
     * @param corpName
     *            The corpName to set.
     */
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    /**
     * @return Returns the status.
     */
    public int getStatus() {
        return status;
    }

    /**
     * @param status
     *            The status to set.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * @return Returns the fundsMoney.
     */
    public float getFundsMoney() {
        return fundsMoney;
    }

    /**
     * @param fundsMoney
     *            The fundsMoney to set.
     */
    public void setFundsMoney(float fundsMoney) {
        this.fundsMoney = fundsMoney;
    }

    /**
     * @return Returns the corpStatus.
     */
    public int getCorpStatus() {
        return corpStatus;
    }

    /**
     * @param corpStatus
     *            The corpStatus to set.
     */
    public void setCorpStatus(int corpStatus) {
        this.corpStatus = corpStatus;
    }

    /**
     * @return Returns the activeValue.
     */
    public int getActiveValue() {
        return activeValue;
    }

    /**
     * @param activeValue
     *            The activeValue to set.
     */
    public void setActiveValue(int activeValue) {
        this.activeValue = activeValue;
    }


    /**
     * @return Returns the themeNum.
     */
    public int getThemeNum() {
        return themeNum;
    }

    /**
     * @param themeNum
     *            The themeNum to set.
     */
    public void setThemeNum(int themeNum) {
        this.themeNum = themeNum;
    }

    /**
     * @return Returns the collectTime.
     */
    public Date getCollectTime() {
        return collectTime;
    }

    /**
     * @param collectTime
     *            The collectTime to set.
     */
    public void setCollectTime(Date collectTime) {
        this.collectTime = collectTime;
    }

    /**
     * @return Returns the fansNum.
     */
    public int getFansNum() {
        return fansNum;
    }

    /**
     * @param fansNum
     *            The fansNum to set.
     */
    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    /**
     * @return Returns the introduce.
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * @param introduce
     *            The introduce to set.
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    /**
     * @return Returns the isListenCourse.
     */
    public int getIsListenCourse() {
        return isListenCourse;
    }

    /**
     * @param isListenCourse
     *            The isListenCourse to set.
     */
    public void setIsListenCourse(int isListenCourse) {
        this.isListenCourse = isListenCourse;
    }

    /**
     * @return Returns the reportNum.
     */
    public int getReportNum() {
        return reportNum;
    }

    /**
     * @param reportNum
     *            The reportNum to set.
     */
    public void setReportNum(int reportNum) {
        this.reportNum = reportNum;
    }

    /**
     * @return Returns the listenNum.
     */
    public int getListenNum() {
        return listenNum;
    }

    /**
     * @param listenNum
     *            The listenNum to set.
     */
    public void setListenNum(int listenNum) {
        this.listenNum = listenNum;
    }

    public boolean getIsCorpVip() {
        return isCorpVip;
    }

    public void setIsCorpVip(boolean isCorpVip) {
        this.isCorpVip = isCorpVip;
    }

}
