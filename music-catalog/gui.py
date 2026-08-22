import customtkinter as ctk
import requests

API_URL = "http://localhost:8080"

# ─── Тема ───
ctk.set_appearance_mode("dark")
ctk.set_default_color_theme("blue")


class MusicCatalogApp(ctk.CTk):
    def __init__(self):
        super().__init__()
        self.title("Music Catalog Manager")
        self.geometry("750x550")
        self.resizable(False, False)

        self.selected_artist_id = None

        self._build_ui()
        self.refresh_artists()

    # ─── Интерфейс ───
    def _build_ui(self):
        # --- Левая часть: список артистов ---
        left = ctk.CTkFrame(self, width=350)
        left.pack(side="left", fill="both", expand=True, padx=10, pady=10)

        ctk.CTkLabel(left, text="Артисты", font=ctk.CTkFont(size=18, weight="bold")).pack(pady=(5, 10))

        self.artist_list = ctk.CTkTextbox(left, state="disabled", font=ctk.CTkFont(family="Consolas", size=13))
        self.artist_list.pack(fill="both", expand=True, padx=5)

        btn_frame = ctk.CTkFrame(left, fg_color="transparent")
        btn_frame.pack(fill="x", padx=5, pady=5)

        ctk.CTkButton(btn_frame, text="Обновить список", command=self.refresh_artists, width=160).pack(side="left", padx=2)

        # --- Правая часть: форма + треки ---
        right = ctk.CTkFrame(self, width=380)
        right.pack(side="right", fill="both", expand=True, padx=(0, 10), pady=10)

        # Форма
        ctk.CTkLabel(right, text="Редактирование", font=ctk.CTkFont(size=18, weight="bold")).pack(pady=(5, 10))

        self.name_entry = ctk.CTkEntry(right, placeholder_text="Имя артиста", width=340)
        self.name_entry.pack(padx=15, pady=5)

        btn_row = ctk.CTkFrame(right, fg_color="transparent")
        btn_row.pack(fill="x", padx=15, pady=5)

        ctk.CTkButton(btn_row, text="Добавить", command=self.create_artist, fg_color="#28a745", hover_color="#218838", width=105).pack(side="left", padx=2)
        ctk.CTkButton(btn_row, text="Обновить", command=self.update_artist, fg_color="#007bff", hover_color="#0069d9", width=105).pack(side="left", padx=2)
        ctk.CTkButton(btn_row, text="Удалить", command=self.delete_artist, fg_color="#dc3545", hover_color="#c82333", width=105).pack(side="left", padx=2)

        ctk.CTkButton(right, text="Очистить выбор", command=self.clear_selection, fg_color="gray", hover_color="#555", width=340).pack(padx=15, pady=(0, 10))

        self.status_label = ctk.CTkLabel(right, text="Выбери артиста из списка", text_color="gray")
        self.status_label.pack(padx=15, pady=(0, 5))

        # Треки
        ctk.CTkLabel(right, text="Треки", font=ctk.CTkFont(size=16, weight="bold")).pack(pady=(10, 5))

        self.track_list = ctk.CTkTextbox(right, state="disabled", font=ctk.CTkFont(family="Consolas", size=12), height=180)
        self.track_list.pack(fill="both", expand=True, padx=10, pady=(0, 10))

        self.refresh_tracks()

    # ─── API: Артисты ───
    def refresh_artists(self):
        try:
            resp = requests.get(f"{API_URL}/artists", timeout=5)
            self.artists = resp.json()
        except Exception as e:
            self.status_label.configure(text=f"Ошибка: {e}", text_color="red")
            return

        self.artist_list.configure(state="normal")
        self.artist_list.delete("1.0", "end")
        for a in self.artists:
            self.artist_list.insert("end", f"  {a['id']:>3}  │  {a['name']}\n")
        self.artist_list.configure(state="disabled")
        self.status_label.configure(text=f"Загружено {len(self.artists)} артистов", text_color="green")

    def create_artist(self):
        name = self.name_entry.get().strip()
        if not name:
            self.status_label.configure(text="Введите имя", text_color="red")
            return
        try:
            resp = requests.post(f"{API_URL}/artists", json={"name": name}, timeout=5)
            if resp.status_code == 200:
                self.status_label.configure(text=f"Создан: {resp.json()['name']}", text_color="green")
                self.name_entry.delete(0, "end")
                self.refresh_artists()
            else:
                err = resp.json().get("error", "Ошибка")
                self.status_label.configure(text=err, text_color="red")
        except Exception as e:
            self.status_label.configure(text=f"Ошибка: {e}", text_color="red")

    def update_artist(self):
        if not self.selected_artist_id:
            self.status_label.configure(text="Выбери артиста из списка", text_color="red")
            return
        name = self.name_entry.get().strip()
        if not name:
            self.status_label.configure(text="Введите новое имя", text_color="red")
            return
        try:
            resp = requests.put(
                f"{API_URL}/artists/{self.selected_artist_id}",
                json={"name": name},
                timeout=5,
            )
            if resp.status_code == 200:
                self.status_label.configure(text=f"Обновлён: {resp.json()['name']}", text_color="green")
                self.name_entry.delete(0, "end")
                self.selected_artist_id = None
                self.refresh_artists()
            else:
                err = resp.json().get("error", "Ошибка")
                self.status_label.configure(text=err, text_color="red")
        except Exception as e:
            self.status_label.configure(text=f"Ошибка: {e}", text_color="red")

    def delete_artist(self):
        if not self.selected_artist_id:
            self.status_label.configure(text="Выбери артиста из списка", text_color="red")
            return
        try:
            resp = requests.delete(f"{API_URL}/artists/{self.selected_artist_id}", timeout=5)
            if resp.status_code == 200:
                self.status_label.configure(text="Удалён", text_color="green")
                self.name_entry.delete(0, "end")
                self.selected_artist_id = None
                self.refresh_artists()
            else:
                err = resp.json().get("error", "Ошибка")
                self.status_label.configure(text=err, text_color="red")
        except Exception as e:
            self.status_label.configure(text=f"Ошибка: {e}", text_color="red")

    def clear_selection(self):
        self.selected_artist_id = None
        self.name_entry.delete(0, "end")
        self.status_label.configure(text="Выбери артиста из списка", text_color="gray")

    # ─── API: Треки ───
    def refresh_tracks(self):
        try:
            resp = requests.get(f"{API_URL}/tracks", timeout=5)
            tracks = resp.json()
        except Exception as e:
            self.track_list.configure(state="normal")
            self.track_list.delete("1.0", "end")
            self.track_list.insert("end", f"  Ошибка загрузки: {e}")
            self.track_list.configure(state="disabled")
            return

        self.track_list.configure(state="normal")
        self.track_list.delete("1.0", "end")
        if not tracks:
            self.track_list.insert("end", "  Нет треков")
        for t in tracks:
            genres = ", ".join(t.get("genres", []))
            self.track_list.insert(
                "end",
                f"  {t['title']}  ({t['durationSeconds']}s)  [{genres}]\n",
            )
        self.track_list.configure(state="disabled")

    # ─── Клик по списку артистов ───
    def _on_artist_click(self, event):
        # Определяем строку по координате клика
        index = self.artist_list.index(f"@{event.x},{event.y}")
        line = self.artist_list.get(f"{index} linestart", f"{index} lineend").strip()
        if not line:
            return
        # Парсим "  1  │  Queen"
        try:
            parts = line.split("│")
            artist_id = int(parts[0].strip())
            artist_name = parts[1].strip()
            self.selected_artist_id = artist_id
            self.name_entry.delete(0, "end")
            self.name_entry.insert(0, artist_name)
            self.status_label.configure(text=f"Выбран: {artist_name} (id={artist_id})", text_color="cyan")
        except (ValueError, IndexError):
            pass


if __name__ == "__main__":
    app = MusicCatalogApp()
    # Привязка клика по списку артистов
    app.artist_list.bind("<ButtonRelease-1>", app._on_artist_click)
    app.mainloop()
