package Recruit.Company;

public class KyungHeeRecruit extends YonseiRecruit {
    @Override
    protected String getBaseRecruitUrl() {
        return "https://khmc.recruiter.co.kr/app/jobnotice";
    }

    @Override
    protected String getCompanyName() {
        return "경희대학교병원";
    }
}
