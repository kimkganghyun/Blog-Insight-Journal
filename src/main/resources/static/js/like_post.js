function likePost(postId) {
    fetch("/posts/" + postId + "/like", {
        method: "POST"
    })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                Swal.fire({
                    icon: 'success',
                    title: '좋아요 되었습니다.',
                    showConfirmButton: false,
                    timer: 1500
                });
                // 좋아요 수 갱신
                document.getElementById("likeCount").textContent = data.likeCount;
            } else {
                Swal.fire({
                    icon: 'error',
                    title: '좋아요에 실패했습니다.',
                    text: data.message,
                });
            }
        });
}
