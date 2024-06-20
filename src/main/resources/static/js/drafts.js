document.addEventListener("DOMContentLoaded", function() {
    const authButtons = document.getElementById("authButtons");
    const userMenu = document.getElementById("userMenu");
    const usernameDisplay = document.getElementById("usernameDisplay");

    fetch("/users/loggedInUser")
        .then(response => response.json())
        .then(data => {
            if (data.username) {
                usernameDisplay.textContent = data.username + "님";
                authButtons.style.display = "none";
                userMenu.style.display = "inline-block";
                loadDrafts(data.username);
            } else {
                location.href = '/login';
            }
        })
        .catch(error => {
            console.error("Error fetching logged in user:", error);
            location.href = '/login';
        });

    userMenu.addEventListener("click", function(event) {
        event.stopPropagation();
        const dropdownMenu = document.getElementById("userDropdown");
        dropdownMenu.style.display = dropdownMenu.style.display === "block" ? "none" : "block";
    });

    window.addEventListener("click", function() {
        const dropdownMenu = document.getElementById("userDropdown");
        dropdownMenu.style.display = "none";
    });
});

function loadDrafts(username) {
    fetch(`/api/posts/drafts?username=${username}`)
        .then(response => response.json())
        .then(data => {
            const draftsContainer = document.getElementById("draftsContainer");
            draftsContainer.innerHTML = "";
            data.forEach(post => {
                const postElement = document.createElement("div");
                postElement.classList.add("draft-post");

                const titleElement = document.createElement("h2");
                titleElement.textContent = post.title;
                postElement.appendChild(titleElement);

                const contentElement = document.createElement("p");
                contentElement.textContent = post.content;
                postElement.appendChild(contentElement);

                const editLink = document.createElement("a");
                editLink.href = `/posts/edit/${post.id}`;
                editLink.textContent = "수정";
                postElement.appendChild(editLink);

                draftsContainer.appendChild(postElement);
            });
        })
        .catch(error => {
            console.error("Error fetching drafts:", error);
        });
}

function logout() {
    fetch("/users/logout", {
        method: "POST"
    }).then(response => {
        if (response.ok) {
            Swal.fire({
                icon: 'success',
                title: '로그아웃 성공',
                showConfirmButton: false,
                timer: 1500
            }).then(() => {
                location.href = '/home';
            });
        } else {
            Swal.fire({
                icon: 'error',
                title: '오류',
                text: '로그아웃 실패',
            });
        }
    }).catch(error => {
        Swal.fire({
            icon: 'error',
            title: '오류',
            text: '로그아웃 실패: ' + error.message,
        });
    });
}
