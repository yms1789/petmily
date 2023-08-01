# 가동중인 awsstudy 도커 중단 및 삭제
sudo docker ps -a -q --filter "name=petmily_pjt_temp_app_1" | grep -q . && docker stop petmily_pjt_temp_app_1 && docker rm petmily_pjt_temp_app_1 | true

# 기존 이미지 삭제
sudo docker rmi ryejin/petmily

# 도커허브 이미지 pull
sudo docker pull ryejin/petmily

# 도커 run
docker run -d -p 8081:8081 -v /home/ec2-user:/config --name petmily_pjt_temp_app_1 ryejin/petmily

# 사용하지 않는 불필요한 이미지 삭제 -> 현재 컨테이너가 물고 있는 이미지는 삭제 안됨
docker rmi -f $(docker images -f "dangling=true" -q) || true