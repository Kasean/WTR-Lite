package com.epolsoft.wtr.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.epolsoft.wtr.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Integer>{

	Report findOneByReportId(Integer reportId);
	
	@Query(nativeQuery = true,value = "call get_list_of_report(:userId)")   // call store procedure with arguments
    List<Report> getListOfReports(@Param("userId") Integer userId);
	
	/*@Query("select r from Report r where r.user.userId = :userId order by projectId")
	List<Report> findByUserIdOrderByProject_ProjectId(@Param("userId") Integer userId);*/
    
	@Query("select r from Report r where r.user.userId = ?1")
    List<Report> findByUserId(Integer userId);
	
	@Query("select r from Report r where r.feature.featureId = :featureId and r.user.userId = :userId")
    List<Report> findByFeatureIdAndUserId(Integer featureId, Integer userId);
	
	@Query("select r from Report r where r.project.projectID = :projectId and r.user.userId = :userId")
    List<Report> findByProjectIDAndUserId(Integer projectId, Integer userId);
	
	@Query("select r from Report r where r.task.id = :taskId and r.user.userId = :userId")
    List<Report> findByIdAndUserId(Integer taskId, Integer userId);
	
	@Query("select r from Report r where r.factor.id = :factorId and r.user.userId = :userId")
    List<Report> findByFactorIdAndUserId(Integer factorId, Integer userId);
	
	@Query("select r from Report r where r.user.userId = :userId and r.date between :dateStart and :dateFinish")
    List<Report> findByUserIdAndDateBetween(@Param("userId") Integer userId, @Param("dateStart") Date dateStart, @Param("dateFinish") Date dateFinish);
	
	@Query("select r from Report r where r.feature.featureId = :featureId and r.user.userId = :userId and r.date between :dateStart and :dateFinish")
    List<Report> findByFeatureIdAndUserIdAndDateBetween(@Param("featureId") Integer featureId, @Param("userId") Integer userId, @Param("dateStart") Date dateStart, @Param("dateFinish") Date dateFinish);
	
	@Query("select r from Report r where r.project.projectID = :projectId and r.user.userId = :userId and r.date between :dateStart and :dateFinish")
    List<Report> findByProjectIDAndUserIdAndDateBetween(@Param("projectId") Integer projectId, @Param("userId") Integer userId, @Param("dateStart") Date dateStart, @Param("dateFinish") Date dateFinish);
	
	@Query("select r from Report r where r.task.id = :taskId and r.user.userId = :userId and r.date between :dateStart and :dateFinish")
    List<Report> findByIdAndUserIdAndDateBetween(@Param("taskId") Integer taskId, @Param("userId") Integer userId, @Param("dateStart") Date dateStart, @Param("dateFinish") Date dateFinish);
	
	@Query("select r from Report r where r.factor.id = :factorId and r.user.userId = :userId and r.date between :dateStart and :dateFinish")
    List<Report> findByFactorIdAndUserIdAndDateBetween(@Param("factorId") Integer factorId, @Param("userId") Integer userId, @Param("dateStart") Date dateStart, @Param("dateFinish") Date dateFinish);
	
	@Query("select r from Report r where r.user.userId = :userId and r.date = :date")
    List<Report> findByUserIdAndDate(@Param("userId") Integer userId, @Param("date") Date date);
	
	@Query("select r from Report r where r.user.userId = :userId and r.feature.featureId = :featureId and r.date = :date")
    List<Report> findByUserIdAndFeatureIdAndDate(@Param("userId") Integer userId, @Param("featureId") Integer featureId, @Param("date") Date date);
	
	@Query("select r from Report r where r.user.userId = :userId and r.project.projectID = :projectId and r.date = :date")
    List<Report> findByUserIdAndProjectIDAndDate(@Param("userId") Integer userId, @Param("projectId") Integer projectId, @Param("date") Date date);
	
	@Query("select r from Report r where r.user.userId = :userId and r.task.id = :taskId and r.date = :date")
    List<Report> findByUserIdAndIdAndDate(@Param("userId") Integer userId, @Param("taskId") Integer taskId, @Param("date") Date date);
	
	@Query("select r from Report r where r.user.userId = :userId and r.factor.id = :factorId and r.date = :date")
    List<Report> findByUserFactorIdAndIdAndDate(@Param("userId") Integer userId, @Param("factorId") Integer factorId, @Param("date") Date date);
	
	/*@Query("select r from Report r where r.date between :dateStart and :dateFinish")
    List<Report> findByDateBetween(@Param("dateStart") Date dateStart, @Param("dateFinish") Date dateFinish);
	
	@Query("select r from Report r where r.date > :date")
    List<Report> findByDateAfter(@Param("date") Date date);*/
}
