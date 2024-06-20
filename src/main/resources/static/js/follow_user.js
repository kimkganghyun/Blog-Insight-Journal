function followUser(userId) {
    fetch("/users/" + userId + "/follow", {
        method: "POST"
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                Swal.fire({
                    icon: 'success',
                    title: '팔로우 되었습니다.',
                    showConfirmButton: false,
                    timer: 1500
                });
                // 팔로우 상태 갱신
                document.getElementById("followButton").textContent = "언팔로우";
                document.getElementById("followButton").setAttribute("onclick", "unfollowUser(" + userId + ")");
            } else {
                Swal.fire({
                    icon: 'error',
                    title: '팔로우에 실패했습니다.',
                    text: data.message,
                });
            }
        });
}

function unfollowUser(userId) {
    fetch("/users/" + userId + "/unfollow", {
        method: "POST"
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                Swal.fire({
                    icon: 'success',
                    title: '언팔로우 되었습니다.',
                    showConfirmButton: false,
                    timer: 1500
                });
                // 팔로우 상태 갱신
                document.getElementById("followButton").textContent = "팔로우";
                document.getElementById("followButton").setAttribute("onclick", "followUser(" + userId + ")");
            } else {
                Swal.fire({
                    icon: 'error',
                    title: '언팔로우에 실패했습니다.',
                    text: data.message,
                });
            }
        });
}
