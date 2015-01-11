<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 客流排名zzx -->
<c:choose>
	<c:when test="${okcMenu.headerdescription == '监控概览'}">
		<%-- 客流监控页面概览内容Start --%>
		<div class="custom_content" id="floorView">
			<div class="custom_title">
				<h1>分层概览</h1>
				<div class="end_time end_time_f">
					<p>截至</p>
					<p class="cur_time"></p>
				</div>
			</div>
			<div id="switch_floor" class="custom_controll">
				<a class="carousel-control left" href="#" >‹</a>
				<a class="carousel-control right" style="right: 45px; " href="#">›</a>
			</div>
			<div class="custom_box" id="passengerView">
				<div class="custom_floor_box border_1">
					<div class="floor_num">一层</div>
					<div class="hot_store">
						<p class="hot_d">
							<span style="float: right;">场内人数</span> <span>热点店铺</span>
						</p>
						<p class="hot_i">
							<span class="hot_num">1,157</span> <span
								class="hot_name">KFC</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top1</p>
					</div>
					<div class="hot_store">
						<p class="hot_i">
							<span class="hot_num">1,025</span> <span
								class="hot_name">JACK JONES</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top2</p>
					</div>
					<div class="hot_store">
						<p class="hot_i">
							<span class="hot_num">995</span> <span class="hot_name">SELECTED</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top3</p>
					</div>
					<div class="hot_store last">
						<p class="hot_i">
							<span class="hot_num">987</span> <span class="hot_name">优衣库</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top4</p>
					</div>
				</div>
				<div class="custom_floor_box border_2">
					<div class="floor_num">二层</div>
					<div class="hot_store">
						<p class="hot_d">
							<span style="float: right;">场内人数</span> <span>热点店铺</span>
						</p>
						<p class="hot_i">
							<span class="hot_num">957</span> <span class="hot_name">大玩家</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top1</p>
					</div>
					<div class="hot_store">
						<p class="hot_i">
							<span class="hot_num">825</span> <span class="hot_name">百货2F</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top2</p>
					</div>
					<div class="hot_store">
						<p class="hot_i">
							<span class="hot_num">346</span> <span class="hot_name">cabbeen</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top3</p>
					</div>
					<div class="hot_store last">
						<p class="hot_i">
							<span class="hot_num">287</span> <span class="hot_name">GXG</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top4</p>
					</div>
				</div>
				<div class="custom_floor_box border_2">
					<div class="floor_num">三层</div>
					<div class="hot_store">
						<p class="hot_d">
							<span style="float: right;">场内人数</span> <span>热点店铺</span>
						</p>
						<p class="hot_i">
							<span class="hot_num">1,013</span> <span
								class="hot_name">百货3F</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top1</p>
					</div>
					<div class="hot_store">
						<p class="hot_i">
							<span class="hot_num">525</span> <span class="hot_name">一九八三</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top2</p>
					</div>
					<div class="hot_store">
						<p class="hot_i">
							<span class="hot_num">328</span> <span class="hot_name">KIKC</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top3</p>
					</div>
					<div class="hot_store last">
						<p class="hot_i">
							<span class="hot_num">187</span> <span class="hot_name">VAAKAV</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top4</p>
					</div>
				</div>
				<div class="custom_floor_box border_2">
					<div class="floor_num">四层</div>
					<div class="hot_store">
						<p class="hot_d">
							<span style="float: right;">场内人数</span> <span>热点店铺</span>
						</p>
						<p class="hot_i">
							<span class="hot_num">1,241</span> <span
								class="hot_name">百货4F</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top1</p>
					</div>
					<div class="hot_store">
						<p class="hot_i">
							<span class="hot_num">1,021</span> <span
								class="hot_name">燕氏小厨</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top2</p>
					</div>
					<div class="hot_store">
						<p class="hot_i">
							<span class="hot_num">628</span> <span class="hot_name">满记甜品</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top3</p>
					</div>
					<div class="hot_store last">
						<p class="hot_i">
							<span class="hot_num">325</span> <span class="hot_name">MISS
								SWEET</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top4</p>
					</div>
				</div>
				<div class="custom_floor_box border_2">
					<div class="floor_num">五层</div>
					<div class="hot_store">
						<p class="hot_d">
							<span style="float: right;">场内人数</span> <span>热点店铺</span>
						</p>
						<p class="hot_i">
							<span class="hot_num">1,015</span> <span
								class="hot_name">百货5F</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top1</p>
					</div>
				</div>
				<div class="custom_floor_box border_2">
					<div class="floor_num">六层</div>
					<div class="hot_store">
						<p class="hot_d">
							<span style="float: right;">场内人数</span> <span>热点店铺</span>
						</p>
						<p class="hot_i">
							<span class="hot_num">846</span> <span class="hot_name">百货6F</span>
							<!--                             <span class="video"></span> -->
						</p>
						<p class="hot_rank">Top1</p>
					</div>
				</div>
			</div>
		</div>
		<%-- 客流监控页面概览内容End --%>
	</c:when>
	<c:otherwise>
		<!-- 楼层索引、截止时间、热点店铺 -->
		<div class="compassBar">
			<div class="search_bar">
				<a href="#" class="search_btn"></a> <input id="querytext"
					class="search_input" placeholder="搜索店铺" type="text">
			</div>
			<span class="compass_icon"></span>
		</div>
		<div class="alarm_level_key">
			<div class="a_l_k">
				<div class="a_l_b">
					<span>&gt;5,000</span> <i class="level_high" tip="high"></i>
				</div>
				<div class="a_l_m">
					<span>&gt;1,000</span> <i class="level_middle" tip="middle"></i>
				</div>
				<div class="a_l_s">
					<span>&gt;1</span> <i class="level_low" tip="low"></i>
				</div>
				<div class="a_l_ss">
					<i class="level_fault" tip="fault"></i>
				</div>
			</div>
		</div>
	</c:otherwise>
</c:choose>
<!-- 客流排名END -->